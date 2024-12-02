# Weighted Fair Queuing Simulation

## Overview

This project demonstrates a **Weighted Fair Queuing (WFQ)** scheduling algorithm in Java. The program simulates how packets are scheduled and processed based on their assigned weights, representing priorities (High, Medium, Low). Each packet belongs to a flow, and higher-weight flows receive proportionally more bandwidth.

---

## Features
1. **Weighted Scheduling**:
   - Packets are processed based on their flow weights, ensuring fairness in resource allocation.
2. **Priority Categorization**:
   - Flows are categorized into `High`, `Medium`, and `Low` priority based on their weight.
3. **Simulation Output**:
   - Real-time console logs display:
     - Time
     - Flow ID
     - Packet size
     - Weight
     - Priority
     - Whether the packet was scheduled
     - Virtual finish time for each packet.

---

## Code Structure

### Classes

#### `Packet`
Represents a network packet with:
- **Attributes**:
  - `flowId`: Unique identifier for the flow.
  - `size`: Size of the packet (bytes).
  - `weight`: Weight of the flow.
  - `arrivalTime`: Time when the packet arrives.
  - `finishTime`: Virtual finish time of the packet (calculated during simulation).
- **Methods**:
  - `getPriorityCategory()`: Categorizes priority as High, Medium, or Low based on weight.

#### `WeightedFairQueuing`
Implements the WFQ scheduling algorithm:
- **Attributes**:
  - `queue`: A priority queue to process packets based on arrival time.
  - `flowFinishTimes`: Tracks the last finish time for each flow.
  - `currentTime`: Simulated clock time for processing packets.
- **Methods**:
  - `addPacket(Packet packet)`: Adds a packet to the queue and calculates its virtual finish time.
  - `simulate()`: Simulates WFQ scheduling and outputs results.

#### `WFQSimulation`
Main class to:
- Initialize the program.
- Define weights for flows.
- Add packets to the scheduler.
- Start the simulation.

---

## How It Works

### Input
1. Define weights for each flow.
2. Add packets to the WFQ scheduler:
   - Specify flow ID, size, weight, and arrival time.

### Output
The console displays:
- Simulation details:
  - Time when packets are processed.
  - Packet details (size, weight, priority).
  - Whether the packet was scheduled.
  - The virtual finish time of each packet.

---

## Usage

### Setup
1. Install [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
2. Clone or download this repository.

### Run the Code
1. Navigate to the project directory.
2. Compile the program:
   ```bash
java WFQSimulation
