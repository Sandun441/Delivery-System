public class MaxHeap {
    private ParcelRequest[] heap;
    int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new ParcelRequest[capacity + 1]; // 1-based indexing
        this.size = 0;

    }

    public void insert(ParcelRequest request) {
        if (size >= capacity) {
            // Handle heap full case (you could expand array here)
            return;
        }
        heap[++size] = request;
        heapifyUp(size);
    }

    public ParcelRequest extractMax() {
        if (size == 0) return null;
        ParcelRequest max = heap[1];
        heap[1] = heap[size--];
        heapifyDown(1);
        return max;
    }


    private void heapifyUp(int index) {
        while (index > 1 && compare(heap[index], heap[parent(index)]) > 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void heapifyDown(int index) {
        while (leftChild(index) <= size) {
            int maxChild = leftChild(index);
            if (rightChild(index) <= size &&
                    compare(heap[rightChild(index)], heap[maxChild]) > 0) {
                maxChild = rightChild(index);
            }
            if (compare(heap[index], heap[maxChild]) >= 0) break;
            swap(index, maxChild);
            index = maxChild;
        }
    }
    private int compare(ParcelRequest a, ParcelRequest b) {
        // 1. Highest priority to VIP customers
        if (a.isVIP != b.isVIP) {
            return b.isVIP ? -1 : 1;
        }

        boolean aSameDay = a.isEffectiveSameDay();
        boolean bSameDay = b.isEffectiveSameDay();
        if (aSameDay != bSameDay) {
            return bSameDay ? -1 : 1;
        }

        // 3. Then nearest deadline (smallest number)
        if (a.deadline != b.deadline) {
            return b.deadline - a.deadline;
        }

        // 4. Then highest order history count
        if (a.orderHistoryCount != b.orderHistoryCount) {
            return a.orderHistoryCount - b.orderHistoryCount;
        }

        // 5. Finally oldest customer (smallest orderTime)
        return b.orderTime - a.orderTime;
    }

    private int parent(int i) { return i / 2; }
    private int leftChild(int i) { return 2 * i; }
    private int rightChild(int i) { return 2 * i + 1; }

    private void swap(int i, int j) {
        ParcelRequest temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

}