package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy {
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize = 0;

    public FileStorageStrategy() {
        for(int i = 0; i < table.length; i++){
            table[i] = new FileBucket();
        }
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int hash(Long k) {
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash(key);
        for (Entry e = table[indexFor(hash, table.length)].getEntry();
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    //проследи, чтобы уже не нужные файлы были удалены (вызови метод remove()
    public void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
        maxBucketSize = (int) Math.min(newCapacity, DEFAULT_INITIAL_CAPACITY);
    }

    public void transfer(FileBucket[] newTable) {
        FileBucket[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry e = src[j].getEntry();
            if (e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i].getEntry();
                    newTable[i].putEntry(e);
                    e = next;
                } while (e != null);
            }
            src[j].remove();
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket e = table[bucketIndex];
        table[bucketIndex].putEntry(new Entry(hash, key, value, e.getEntry()));
        //удваивать количество ведер, когда размер одного из ведер (файлов) стал больше bucketSizeLimit
        if (size++ >= bucketSizeLimit)
            resize(2 * table.length);
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null)
            return false;
        for (FileBucket aTable : table) {
            for (Entry e = aTable.getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        addEntry(hash(key), key, value, indexFor(hash(key), table.length));
    }

    @Override
    public Long getKey(String value) {
        if (value == null)
            return 0l;
        for (FileBucket aTable : table) {
            for (Entry e = aTable.getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return aTable.getEntry().getKey();
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        if (getEntry(key) != null) {
            return getEntry(key).getValue();
        } else {
            return null;
        }
    }
}
