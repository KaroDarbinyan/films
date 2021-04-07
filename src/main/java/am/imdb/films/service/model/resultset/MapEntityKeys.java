package am.imdb.films.service.model.resultset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapEntityKeys<K, V> {

    private K key;
    private V value;

    public Map<K, V> toMap(Collection<MapEntityKeys<K, V>> mapEntityKeys) {
        return mapEntityKeys.stream()
                .collect(Collectors.toMap(MapEntityKeys::getKey, MapEntityKeys::getValue));
    }

    public Map<V, K> toReverseMap(Collection<MapEntityKeys<K, V>> mapEntityKeys) {
        return mapEntityKeys.stream()
                .collect(Collectors.toMap(MapEntityKeys::getValue, MapEntityKeys::getKey));
    }

}
