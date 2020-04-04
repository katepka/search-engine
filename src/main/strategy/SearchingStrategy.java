package main.strategy;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchingStrategy {
    Set<String> search(Map<String, List<Integer>> indexes,
                       Map<Integer, String> datasets,
                       String searchedData);
}
