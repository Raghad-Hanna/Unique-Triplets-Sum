public record IndexedValue(int value, Integer index) implements Comparable<IndexedValue> {
    @Override
    public int compareTo(IndexedValue o) {
        return this.value - o.value;
    }
}
