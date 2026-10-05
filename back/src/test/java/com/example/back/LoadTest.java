package com.example.back;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoadTest {
    private static final int NODES = 100;
    private static final int INTERVAL_MS = 30_000; // 30 секунд
    private static final String ENDPOINT = "http://localhost:8080/api/node/changeable";
    private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTk9ERSIsInNlcnZlcklkIjoxLCJ1c2VySWQiOjEsInN1YiI6IjEiLCJpYXQiOjE3NjAxNjQ2NTd9.NUaTgiROt7OQl7mXMV8HcIV6zXLNPgK68L0oIa8IiGs";

    private static final String JSON = """
            {
               "up": true,
               "uptime": 45379.0,
               "failed_logins": -1,
               "cpu": {
                 "cpu_percent_total_load": 6.625,
                 "cores": [
                   {
                     "core_index": 1,
                     "core_percent_load": 8.615
                   },
                   {
                     "core_index": 2,
                     "core_percent_load": 4.625
                   },
                   {
                     "core_index": 3,
                     "core_percent_load": 18.24
                   },
                   {
                     "core_index": 4,
                     "core_percent_load": 6.685
                   },
                   {
                     "core_index": 5,
                     "core_percent_load": 11.78
                   },
                   {
                     "core_index": 6,
                     "core_percent_load": 8.31
                   },
                   {
                     "core_index": 7,
                     "core_percent_load": 4.83
                   },
                   {
                     "core_index": 8,
                     "core_percent_load": 4.145
                   },
                   {
                     "core_index": 9,
                     "core_percent_load": 3.915
                   },
                   {
                     "core_index": 10,
                     "core_percent_load": 3.195
                   },
                   {
                     "core_index": 11,
                     "core_percent_load": 4.86
                   },
                   {
                     "core_index": 12,
                     "core_percent_load": 4.0
                   },
                   {
                     "core_index": 13,
                     "core_percent_load": 4.66
                   },
                   {
                     "core_index": 14,
                     "core_percent_load": 4.005
                   },
                   {
                     "core_index": 15,
                     "core_percent_load": 9.215
                   },
                   {
                     "core_index": 16,
                     "core_percent_load": 6.525
                   }
                 ],
                 "current_freq": 3401.0,
                 "cpu_time_user": 10.344,
                 "cpu_time_system": 10.219,
                 "cpu_time_idle": 321.188,
                 "cpu_time_interrupt": 0.312,
                 "cpu_time_dpc": 0.375,
                 "ctx_switches": 524436,
                 "interrupts": 396468,
                 "soft_interrupts": 0,
                 "syscalls": 1872401
               },
               "memory": {
                 "total": 16270.75,
                 "used": 14427.612,
                 "free": 1843.138,
                 "cached": 0.0,
                 "percent": 88.68
               },
               "swap": {
                 "total": 10240.0,
                 "used": 564.77,
                 "free": 9675.23,
                 "percent": 5.5
               },
               "network_connections": {
                 "tcp": 110,
                 "udp": 0
               },
               "net_interfaces": [
                 {
                   "sent": 81816,
                   "recv": 91039,
                   "packets_sent": 302,
                   "packets_recv": 277,
                   "err_in": 0,
                   "err_out": 0,
                   "drop_in": 0,
                   "drop_out": 0
                 }
               ],
               "disk_partitions": [
                 {
                   "device": "C:\\\\",
                   "mountpoint": "C:\\\\",
                   "fstype": "NTFS",
                   "opts": "rw,fixed",
                   "total": 487727.18,
                   "used": 356246.83,
                   "free": 131480.36,
                   "used_percent": 73.0
                 },
                 {
                   "device": "D:\\\\",
                   "mountpoint": "D:\\\\",
                   "fstype": "NTFS",
                   "opts": "rw,fixed",
                   "total": 953852.0,
                   "used": 685621.43,
                   "free": 268230.56,
                   "used_percent": 71.9
                 }
               ],
               "disk_io": {
                 "PhysicalDrive0": {
                   "read_count": 263,
                   "write_count": 339,
                   "read": 6.344,
                   "write": 24.536
                 },
                 "PhysicalDrive1": {
                   "read_count": 0,
                   "write_count": 23,
                   "read": 0.0,
                   "write": 0.25
                 }
               },
               "gpu": [
                 {
                   "id": 0,
                   "uuid": "GPU-6e244b0b-2121-591a-fadd-53b7b2610d83",
                   "name": "NVIDIA GeForce RTX 3060 Ti",
                   "driver_version": "572.16",
                   "memory_total": 8192.0,
                   "load_percent": 2.6,
                   "memory_used": 730.25,
                   "memory_free": 7294.75,
                   "memory_used_percent": 8.915,
                   "temperature": 38.1
                 }
               ],
               "timestamp": "2025-10-11T06:37:43Z",
               "agent_resource_usage": {
                 "cpu_percent_avg": 0.05,
                 "memory_mb_max": 50.453
               }
             }
    """;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NODES);

        for (int i = 0; i < NODES; i++) {
            executor.submit(() -> {
                while (true) {
                    try {
                        sendMetrics();
                        Thread.sleep(INTERVAL_MS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void sendMetrics() throws Exception {
        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-API-Key", JWT);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(JSON.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Sent metrics, response: " + responseCode);
        conn.disconnect();
    }
}
