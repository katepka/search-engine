package main.strategy;

import java.util.*;

public class SearchingStrategyAny implements SearchingStrategy {
    @Override
    public Set<String> search(Map<String, List<Integer>> indexes,
                               Map<Integer, String> datasets,
                               String query) {
        Set<String> result = new HashSet<>();
        String[] words = query.split(" ");

        for (String word : words) {
            if (indexes.containsKey(word.toLowerCase())) {
                for (int i : indexes.get(word.toLowerCase())) {
                    result.add(datasets.get(i));
                }
            }
        }
        return result;
    }
}
