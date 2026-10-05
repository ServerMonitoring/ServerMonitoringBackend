package com.example.back.service.impl;

import com.example.back.dto.request.*;
import com.example.back.exception.RequestArgumentException;
import com.example.back.exception.AuthenticationFailedException;
import com.example.back.dto.response.MetricResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.*;
import com.example.back.repository.MetricRepository;
import com.example.back.repository.ServerRepository;
import com.example.back.service.*;
import com.example.back.service.security.JwtService;
import com.example.back.util.EntityUtils;
import com.example.back.util.ExtractCriteria;
import com.example.back.util.alert.AlertEvaluationService;
import com.example.back.util.criteriaSpecification.SimpleMetricSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    private final MetricRepository metricRepository;
    private final AlertEvaluationService alertEvaluationService;
    private final ServerRepository serverRepository;
    private final JwtService jwtService;


    public MetricServiceImpl(MetricRepository metricRepository, AlertEvaluationService alertEvaluationService, ServerRepository serverRepository, JwtService jwtService) {
        this.metricRepository = metricRepository;
        this.alertEvaluationService = alertEvaluationService;
        this.serverRepository = serverRepository;
        this.jwtService = jwtService;

    }


    @Override
    public List<MetricResponseDTO> getMetricsByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria) {
        Specification<Metric> metricSpecification = SimpleMetricSpecification.bySimpleCriteria(metricCriteria, baseCriteria);
        List<Metric> metrics = metricRepository.findAll(metricSpecification);
        return metrics.stream().map(MetricResponseDTO::toDTO).toList();
    }


    @Override
    @Transactional
    public void saveStaticMetrics(String nodeToken, StaticMetricDTORequest dtoRequest){
        Long serverId = jwtService.extractServerId(nodeToken);
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
        assertCurrentNodeToken(nodeToken, server);

        EntityUtils.updateIfChanged(server::getHostname, server::setHostname, dtoRequest.getHostname());
        EntityUtils.updateIfChanged(server::getOsInfo, server::setOsInfo, dtoRequest.getOs());
        EntityUtils.updateIfChanged(server::getCpuModel, server::setCpuModel, dtoRequest.getCpuModel());
        EntityUtils.updateIfChanged(server::getCpuCountCores, server::setCpuCountCores, dtoRequest.getCpuCountCores());
        EntityUtils.updateIfChanged(server::getCpuCountCoresPhysical, server::setCpuCountCoresPhysical, dtoRequest.getCpuCountCoresPhysical());
        EntityUtils.updateIfChanged(server::getMinFreq, server::setMinFreq, dtoRequest.getMinFreq());
        EntityUtils.updateIfChanged(server::getMaxFreq, server::setMaxFreq, dtoRequest.getMaxFreq());

        server.setLastSeenAt(Instant.now());
        server.setOnline(true);
        serverRepository.save(server);
    }

    @Override
    @Transactional
    public void saveMetrics(String nodeToken, MetricDTORequest metricDTORequest) {
        if (metricDTORequest == null || metricDTORequest.getTimestamp() == null) {
            throw new RequestArgumentException("Metric payload and timestamp are required");
        }
        Long serverId = jwtService.extractServerId(nodeToken);
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
        assertCurrentNodeToken(nodeToken, server);

        if (metricDTORequest.getEventId() != null && !metricDTORequest.getEventId().isBlank()) {
            var existingMetric = metricRepository.findByEventId(metricDTORequest.getEventId());
            if (existingMetric.isPresent()) {
                if (!serverId.equals(existingMetric.get().getServer().getServerId())) {
                    throw new RequestArgumentException("Metric event ID is already used by another server");
                }
                server.setLastSeenAt(Instant.now());
                server.setOnline(true);
                serverRepository.save(server);
                return;
            }
        }

        Metric metric = new Metric();
        metric.setEventId(metricDTORequest.getEventId());
        metric.setTimestamp(metricDTORequest.getTimestamp());
        metric.setUptime(metricDTORequest.getUptime());
        metric.setNetSent(metricDTORequest.getNetSent());
        metric.setNetRecv(metricDTORequest.getNetRecv());
        metric.setNetErrors(metricDTORequest.getNetErrors());
        metric.setNetDrops(metricDTORequest.getNetDrops());
        metric.setFailedLogins(metricDTORequest.getFailedLogins());
        metric.setActiveConnections(metricDTORequest.getActiveConnections());
        metric.setDiskTotalUsedPercent(metricDTORequest.getDiskTotalUsedPercent());
        metric.setDiskTotalAvailable(metricDTORequest.getDiskTotalAvailable());
        metric.setServer(server);


        //TODO можно сделать хелпер метод внутри metrics двухсторонней связи ( и для remove ??? )
     /*   public void addDisk(Disk disk) {
            if (disks == null) disks = new ArrayList<>();
            disks.add(disk);
            disk.setMetric(this);
        }

        for (DiskDTORequest dto : metricDTORequest.getDisks()) {
            metric.addDisk(DiskDTORequest.toModel(dto));
        }*/
        // Устанавливаем дочерние объекты в родителя
        if (metricDTORequest.getMemory() != null) {
            Memory memory = MemoryDTORequest.toModel(metricDTORequest.getMemory());
            memory.setMetric(metric);
            metric.setMemory(memory);
        }

        if (metricDTORequest.getSwap() != null) {
            Swap swap = SwapDTORequest.toModel(metricDTORequest.getSwap());
            swap.setMetric(metric);
            metric.setSwap(swap);
        }

        if (metricDTORequest.getCpu() != null) {
            CPU cpu = CPUDTORequest.toModel(metricDTORequest.getCpu());
            cpu.setMetric(metric);
            List<Core> cores = safeList(metricDTORequest.getCpu().getCores()).stream()
                    .filter(java.util.Objects::nonNull)
                    .map(dto -> {
                        Core core = CoresDTORequest.toModel(dto);
                        core.setCpu(cpu);
                        return core;
                    }).toList();
            cpu.setCores(cores);
            metric.setCpu(cpu);
        }

        if (metricDTORequest.getNetworkConnection() != null) {
            NetworkConnection networkConnection = NetworkConnectionDTORequest.toModel(metricDTORequest.getNetworkConnection());
            networkConnection.setMetric(metric);
            metric.setNetworkConnection(networkConnection);
        }

        List<Disk> disks = safeList(metricDTORequest.getDisks()).stream()
                .filter(java.util.Objects::nonNull)
                .map(dto -> {
                    Disk disk = DiskDTORequest.toModel(dto);
                    disk.setMetric(metric);
                    return disk;
                }).toList();
        metric.setDisks(disks);

        List<DiskIO> diskIOs = (metricDTORequest.getDiskIo() == null ? java.util.Map.<String, DiskIODTORequest>of() : metricDTORequest.getDiskIo()).entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .map(entry -> {
                    DiskIO diskIO = DiskIODTORequest.toModel(entry.getKey(), entry.getValue());
                    diskIO.setMetric(metric);
                    return diskIO;
                }).toList();
        metric.setDiskIo(diskIOs);

        List<GPU> gpus = safeList(metricDTORequest.getGpu()).stream()
                .filter(java.util.Objects::nonNull)
                .map(dto -> {
                    GPU gpu = GPUDTORequest.toModel(dto);
                    gpu.setMetric(metric);
                    return gpu;
                }).toList();
        metric.setGpu(gpus);

        List<NetInterface> netInterfaces = safeList(metricDTORequest.getNetInterfaces()).stream()
                .filter(java.util.Objects::nonNull)
                .map(dto -> {
                    NetInterface netInterface = NetInterfaceDTORequest.toModel(dto);
                    netInterface.setMetric(metric);
                    return netInterface;
                }).toList();
        metric.setNetInterfaces(netInterfaces);

        // Сохраняем только родителя — каскад сохранит дочерние
        metricRepository.save(metric);
        alertEvaluationService.evaluateAndSaveAlerts(server,metricDTORequest,server.getUsers().getPreferredLanguage());

        server.setLastSeenAt(Instant.now());
        server.setOnline(true);
        serverRepository.save(server);
    }

    private static <T> List<T> safeList(List<T> values) {
        return values == null ? List.of() : values;
    }

    private void assertCurrentNodeToken(String token, Server server) {
        int currentVersion = server.getNodeTokenVersion() == null ? 0 : server.getNodeTokenVersion();
        if (!Integer.valueOf(currentVersion).equals(jwtService.extractNodeTokenVersion(token))) {
            throw new AuthenticationFailedException("Node token has been revoked; issue a new token");
        }
    }


}
