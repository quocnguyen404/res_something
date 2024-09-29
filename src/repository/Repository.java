package repository;

public interface Repository<Key, Value>
{
    public Value get(Key key);
    public void add(Key key, Value value);
    public void delete(Key key);
}
