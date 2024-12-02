import java.util.*;

class Packet {
    int flowId; // ID of the flow
    int size; // Size of the packet (in bytes)
    int weight; // Weight of the flow
    int arrivalTime; // Arrival time of the packet
    double finishTime; // Virtual finish time of the packet

    public Packet(int flowId, int size, int weight, int arrivalTime) {
        this.flowId = flowId;
        this.size = size;
        this.weight = weight;
        this.arrivalTime = arrivalTime;
        this.finishTime = 0; // Default to 0, will be calculated later
    }

    // Method to categorize priority
    public String getPriorityCategory() {
        if (weight >= 3)
            return "High";
        else if (weight == 2)
            return "Medium";
        else
            return "Low";
    }
}

class WeightedFairQueuing {
    private PriorityQueue<Packet> queue;
    private Map<Integer, Double> flowFinishTimes; // Tracks the last virtual finish time of each flow
    private int currentTime;

    public WeightedFairQueuing(Map<Integer, Integer> flowWeights) {
        this.queue = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));
        this.flowFinishTimes = new HashMap<>();
        for (Integer flowId : flowWeights.keySet()) {
            flowFinishTimes.put(flowId, 0.0); // Initialize virtual finish times for all flows
        }
        this.currentTime = 0;
    }

    // Add a packet to the queue
    public void addPacket(Packet packet) {
        // Calculate the virtual finish time based on the flow's last finish time
        double previousFinishTime = flowFinishTimes.getOrDefault(packet.flowId, 0.0);
        packet.finishTime = Math.max(previousFinishTime, packet.arrivalTime) + ((double) packet.size / packet.weight);
        flowFinishTimes.put(packet.flowId, packet.finishTime); // Update the flow's last finish time
        queue.offer(packet);
    }

    // Simulate the WFQ algorithm
    public void simulate() {
        System.out.println("Simulating WFQ Scheduling...");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-20s\n",
                "Time", "Flow ID", "Size", "Weight", "Priority", "Scheduled", "Finish Time");

        while (!queue.isEmpty()) {
            List<Packet> currentBatch = new ArrayList<>();

            // Collect all packets that arrived at or before the current time
            while (!queue.isEmpty() && queue.peek().arrivalTime <= currentTime) {
                currentBatch.add(queue.poll());
            }

            if (currentBatch.isEmpty()) {
                currentTime++; // No packets to process, advance time
                continue;
            }

            // Process packets in the current batch
            for (Packet packet : currentBatch) {
                int timeSlice = packet.weight; // Allocate bandwidth proportional to weight
                System.out.printf("%-10d %-10d %-10d %-10d %-10s %-10s %-20.2f\n",
                        currentTime, packet.flowId, packet.size, packet.weight,
                        packet.getPriorityCategory(), "YES", packet.finishTime);
                currentTime += timeSlice; // Simulate processing time
            }
        }
    }
}

public class WFQSimulation {
    public static void main(String[] args) {
        // Define weights for each flow (flowId -> weight)
        Map<Integer, Integer> flowWeights = new HashMap<>();
        flowWeights.put(1, 3); // Flow 1 has weight 3
        flowWeights.put(2, 2); // Flow 2 has weight 2
        flowWeights.put(3, 3); // Flow 3 has weight 3
        flowWeights.put(4, 2); // Flow 4 has weight 2

        // Create the WFQ scheduler
        WeightedFairQueuing wfq = new WeightedFairQueuing(flowWeights);

        // Add packets to the system
        wfq.addPacket(new Packet(1, 500, 3, 0));
        wfq.addPacket(new Packet(2, 300, 2, 1));
        wfq.addPacket(new Packet(3, 1000, 3, 2));
        wfq.addPacket(new Packet(1, 200, 1, 3));
        wfq.addPacket(new Packet(4, 400, 2, 4));
        wfq.addPacket(new Packet(2, 4000, 1, 5));
        wfq.addPacket(new Packet(3, 3500, 2, 6));

        // Simulate the WFQ algorithm
        wfq.simulate();
    }
}
