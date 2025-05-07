package com.example.back.service.impl;

import com.example.back.dto.request.*;
import com.example.back.model.*;
import com.example.back.repository.MetricRepository;
import com.example.back.repository.ServerRepository;
import com.example.back.service.*;
import com.example.back.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    private final MetricRepository metricRepository;

    private final ServerRepository serverRepository;

    @Autowired
    public MetricServiceImpl(MetricRepository metricRepository, ServerRepository serverRepository ) {
        this.metricRepository = metricRepository;
        this.serverRepository = serverRepository;
    }

    @Override
    @Transactional
    public void saveStaticMetrics(Long serverId, StaticMetricDTORequest dtoRequest){
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));

        boolean changed = false;

        changed |= EntityUtils.updateIfChanged(server::getHostname, server::setHostname, dtoRequest.getHostname());
        changed |= EntityUtils.updateIfChanged(server::getOsInfo, server::setOsInfo, dtoRequest.getOs());
        changed |= EntityUtils.updateIfChanged(server::getCpuModel, server::setCpuModel, dtoRequest.getCpuModel());
        changed |= EntityUtils.updateIfChanged(server::getCpuCountCores, server::setCpuCountCores, dtoRequest.getCpuCountCores());
        changed |= EntityUtils.updateIfChanged(server::getCpuCountCoresPhysical, server::setCpuCountCoresPhysical, dtoRequest.getCpuCountCoresPhysical());
        changed |= EntityUtils.updateIfChanged(server::getMinFreq, server::setMinFreq, dtoRequest.getMinFreq());
        changed |= EntityUtils.updateIfChanged(server::getMaxFreq, server::setMaxFreq, dtoRequest.getMaxFreq());

        if (changed){
            serverRepository.save(server);
        }
    }

    @Override
    @Transactional
    public void saveMetrics(Long serverId, MetricDTORequest metricDTORequest) {

        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));

        Metric metric = new Metric();
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
        Memory memory = MemoryDTORequest.toModel(metricDTORequest.getMemory());
        memory.setMetric(metric);
        metric.setMemory(memory);

        Swap swap = SwapDTORequest.toModel(metricDTORequest.getSwap());
        swap.setMetric(metric);
        metric.setSwap(swap);



        CPU cpu = CPUDTORequest.toModel(metricDTORequest.getCpu());
        cpu.setMetric(metric);

        List<Core> cores = metricDTORequest.getCpu().getCores().stream()
                .map(dto ->{
                    Core core = CoresDTORequest.toModel(dto);
                    core.setCpu(cpu);
                    return core;
                }).toList();
        cpu.setCores(cores);
        metric.setCpu(cpu);

        NetworkConnection networkConnection = NetworkConnectionDTORequest.toModel(metricDTORequest.getNetworkConnection());
        networkConnection.setMetric(metric);
        metric.setNetworkConnection(networkConnection);

        List<Disk> disks = metricDTORequest.getDisks().stream()
                .map(dto -> {
                    Disk disk = DiskDTORequest.toModel(dto);
                    disk.setMetric(metric);
                    return disk;
                }).toList();
        metric.setDisks(disks);

        List<DiskIO> diskIOs = metricDTORequest.getDiskIo().entrySet().stream()
                .map(entry -> {
                    DiskIO diskIO = DiskIODTORequest.toModel(entry.getKey(), entry.getValue());
                    diskIO.setMetric(metric);
                    return diskIO;
                }).toList();
        metric.setDiskIo(diskIOs);

        List<GPU> gpus = metricDTORequest.getGpu().stream()
                .map(dto -> {
                    GPU gpu = GPUDTORequest.toModel(dto);
                    gpu.setMetric(metric);
                    return gpu;
                }).toList();
        metric.setGpu(gpus);

        List<NetInterface> netInterfaces = metricDTORequest.getNetInterfaces().stream()
                .map(dto -> {
                    NetInterface netInterface = NetInterfaceDTORequest.toModel(dto);
                    netInterface.setMetric(metric);
                    return netInterface;
                }).toList();
        metric.setNetInterfaces(netInterfaces);

        // Сохраняем только родителя — каскад сохранит дочерние
        metricRepository.save(metric);
    }
}
