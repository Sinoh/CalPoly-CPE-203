public class MyMapEntry<K,V>{
    
    public final K key;
    public final V value;
    
    public MyMapEntry(K key, V value){
        this.key = key;
        this.value = value;
    }
    
    public K getKey(){
        return key;
    }
    
    public V getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return "MyMapEntry(Key: " + key + ", " + value + ")";
    }
    
    
}