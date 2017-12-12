package helper;

public class ComboItem 
{
    private String key;

    public ComboItem(String key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return key;
    }

    public String getKey()
    {
        return key;
    }
}
