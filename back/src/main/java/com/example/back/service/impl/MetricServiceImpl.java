package com.example.back.service.impl;

import com.example.back.dto.request.*;
import com.example.back.model.*;
import com.example.back.repository.MetricRepository;
import com.example.back.repository.ServerRepository;
import com.example.back.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        // Устанавливаем дочерние объекты в родителя
        Memory memory = MemoryDTORequest.toModel(metricDTORequest.getMemory());
        memory.setMetric(metric);
        metric.setMemory(memory);

        Swap swap = SwapDTORequest.toModel(metricDTORequest.getSwap());
        swap.setMetric(metric);
        metric.setSwap(swap);

        CPU cpu = CPUDTORequest.toModel(metricDTORequest.getCpu());
        cpu.setMetric(metric);
        metric.setCpu(cpu);

        NetworkConnection networkConnection = NetworkConnectionDTORequest.toModel(metricDTORequest.getNetworkConnection());
        networkConnection.setMetric(metric);
        metric.setNetworkConnection(networkConnection);

        List<Disk> disks = metricDTORequest.getDisks().stream()
                .map(dto -> {
                    Disk disk = DiskDTORequest.toModel(dto);
                    disk.setMetric(metric);
                    return disk;
                }).collect(Collectors.toList());
        metric.setDisks(disks);

        List<DiskIO> diskIOs = metricDTORequest.getDiskIo().stream()
                .map(dto -> {
                    DiskIO diskIO = DiskIODTORequest.toModel(dto);
                    diskIO.setMetric(metric);
                    return diskIO;
                }).collect(Collectors.toList());
        metric.setDiskIo(diskIOs);

        List<GPU> gpus = metricDTORequest.getGpu().stream()
                .map(dto -> {
                    GPU gpu = GPUDTORequest.toModel(dto);
                    gpu.setMetric(metric);
                    return gpu;
                }).collect(Collectors.toList());
        metric.setGpu(gpus);

        List<NetInterface> netInterfaces = metricDTORequest.getNetInterfaces().stream()
                .map(dto -> {
                    NetInterface netInterface = NetInterfaceDTORequest.toModel(dto);
                    netInterface.setMetric(metric);
                    return netInterface;
                }).collect(Collectors.toList());
        metric.setNetInterfaces(netInterfaces);

        // Сохраняем только родителя — каскад сохранит дочерние
        metricRepository.save(metric);
    }
}
