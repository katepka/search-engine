package main.strategy;

import java.util.*;

public class SearchingStrategyNone implements  SearchingStrategy {
    @Override
    public Set<String> search(Map<String, List<Integer>> indexes,
                              Map<Integer, String> datasets,
                              String query) {
        Set<String> result = new HashSet<>();
        String[] words = query.split(" ");

        for (int index : datasets.keySet()) {
            boolean hasWord = false;
            String person = datasets.get(index);
            for (String word : words) {
                if (indexes.containsKey(word.toLowerCase())) {
                    if (indexes.get(word.toLowerCase()).contains(index)) {
                        hasWord = true;
                        break;
                    }
                }
            }
            if (!hasWord) {
                result.add(person);
            }
        }
        return result;
    }
}
