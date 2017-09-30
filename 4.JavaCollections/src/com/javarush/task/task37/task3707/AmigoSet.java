package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet implements Serializable, Cloneable, Set {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<E, Object>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<E, Object>(Integer.max(16, (int) Math.ceil(collection.size() / .75f)));
        addAll(collection);
    }

    @Override
    public Iterator iterator() {
        Set<E> keySet = map.keySet();
        return keySet.iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        map.remove(o);
        return super.remove(o);
    }

    @Override
    public boolean add(Object e) {
        int startsize = map.size();
        map.put((E) e, PRESENT);

        if (map.size() > startsize) {
            return true;
        }
        return false;
    }

    @Override
    public Object clone() {
        AmigoSet copy;
        try {
            copy = (AmigoSet) super.clone();
            copy.map = (HashMap) map.clone();
        } catch (Exception e) {
            throw new InternalError(e);
        }

        return copy;
    }

    private void writeObject(ObjectOutputStream oos) {

        float loadFactor = HashMapReflectionHelper.callHiddenMethod(map, "loadFactor");
        int buckets = HashMapReflectionHelper.callHiddenMethod(map, "capacity");
        try {
            oos.defaultWriteObject();
            oos.writeInt(buckets);
            oos.writeFloat(loadFactor);
        } catch (IOException e) {

        }
    }

    private void readObject(ObjectInputStream s) {
        try {
            s.defaultReadObject();
            int bucket = s.readInt();
            float loadFactor = s.readFloat();
            HashMap<E, Object> map = new HashMap<E, Object>(bucket, loadFactor);

        } catch (Exception e) {

        }


    }
}
