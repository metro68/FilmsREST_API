package Films;

import java.util.Comparator;
import java.util.Map;

public class FilmSortbyValue implements Comparator<Map<String, Object>>
{
    private final String key;

    public FilmSortbyValue(String key)
    {
        this.key = key;
    }

    public int compare(Map<String, Object> first,
                       Map<String, Object> second)
    {
        Object firstValue = first.get(key);
        Object secondValue = second.get(key);
        return (firstValue.toString().compareTo(secondValue.toString()));
    }
}

