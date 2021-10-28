import java.util.List;
import java.util.ArrayList;

// You may not use any library classes other than List and ArrayList,
// above, to implement your map.  If the string "java." or "javax."
// occurs in this file after this, your submission will be rejected.


// DO NOT MODIFY ANYTHING IN THIS FILE UNTIL THIS LINE!
// Fill in you class after this.  Your submission will be 
// rejected by checkgit and by the autograder
// if you modify anything before this comment block.

/**
 * This class implements a map from any key type K to any value
 * type V.  The K type must have a valid equals() and hashCode() 
 * implementations.  MyMap<K, V> supports a public constructor that
 * takes a single int argument, giving the number of buckets for the
 * internal hashtable.
 * <p>
 *
 * MyMap<K, V> supports a get() operation that takes
 * a key value, and delivers null if the key is not found, or a value
 * of type V (or a descendant of V) if the key is found.  The return type
 * of get() is V.
 * <p>
 * MyMap<K, V> further supports a put() operation that takes a key and a value,
 * in that order.  The value is associated with that key value, replacing any
 * other value that might have been stored at that key.
 * <p>
 * MyMap<K, V> also supports a method called getEntries() that takes no
 * arguments, and returns a List<MyMapEntry<K, V>> containing all of the
 * entries currently in the map.  MyMapEntry<K, V> has public final fields 
 * called "key" and "value".  
 * <p>
 * Finally, MyMap<K, V> supports a debugging method called getBuckets().
 * It delivers a List<List<MyMapEntry<K, V>>>, with one entry for each 
 * bucket in the internal hash table.  Because this is just a debugging
 * method, it's OK to return internal data structures; MyMap<K, V> needen't
 * make a defensive copy.  (A defensive copy is when you make a copy of
 * a data structure and return the copy, so a caller can't modify your
 * internal data structures).
 * <p>
 * All of the above methods are public.
 */

public class MyMap<K, V>{

    private List<List<MyMapEntry<K,V>>> map;
    private int size;

    public MyMap(int size) {
        this.size = size;
        this.map = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            map.add(new ArrayList<MyMapEntry<K, V>>());
        }
    }

    public V get(K key) {
        int hash = (key.hashCode() + size) % size;
        List<MyMapEntry<K,V>> bucket = map.get(hash);
        for (MyMapEntry<K,V> entry : bucket){
            if (entry.getKey().equals(key)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void put(K key, V value){
        int hash = key.hashCode() % size;
        List<MyMapEntry<K,V>> bucket = map.get(hash);
        if (bucket.size() == 0){
            bucket.add(new MyMapEntry<>(key, value));
        }else{
            for (MyMapEntry<K, V> entry: bucket){
                if (entry.getKey().equals(key)){
                    int index = bucket.indexOf(entry);
                    bucket.set(index, new MyMapEntry<K, V>(key, value));
                    return;
                }
            }
            bucket.add(new MyMapEntry<>(key, value));
        }
    }

    public List<MyMapEntry<K,V>> getEntries(){
        List<MyMapEntry<K,V>> entries = new ArrayList<MyMapEntry<K,V>>();
        for (List<MyMapEntry<K,V>> bucket: map){
            for (MyMapEntry<K,V> entry: bucket){
                entries.add(entry);
            }
        }
        return entries;
    }

    public List<List<MyMapEntry<K,V>>> getBuckets(){
        return map;
    }

}


