package main;

import main.strategy.SearchingStrategy;
import main.strategy.SearchingStrategyAll;
import main.strategy.SearchingStrategyAny;
import main.strategy.SearchingStrategyNone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Application {
    private static Map<Integer, String> datasets = new HashMap<>();
    private static Map<String, List<Integer>> invertedIndex = new HashMap<>();
    private static SearchingStrategy searchingStrategy;

    public static void main(String[] args) {

        if (args[0].equals("--data")) {
            File file = new File(args[1]);

            int count = 0;
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNext()) {
                    datasets.put(count, sc.nextLine());
                    count++;
                }
                for (Map.Entry<Integer, String> entry : datasets.entrySet()) {
                    String[] dataset = entry.getValue().split(" ");
                    for (String data : dataset) {
                        if (invertedIndex.containsKey(data.toLowerCase())) {
                            invertedIndex.get(data.toLowerCase()).add(entry.getKey());
                        } else {
                            List<Integer> n = new ArrayList<>();
                            n.add(entry.getKey());
                            invertedIndex.put(data.toLowerCase(), n);
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try (Scanner scanner = new Scanner(System.in)) {
            String input = null;
            while (!"0".equals(input)) {
                System.out.println("=== Menu ===");
                System.out.println("1. Find a person");
                System.out.println("2. Print all people");
                System.out.println("0. Exit");

                input = scanner.nextLine();
                switch (input) {
                    case "0":
                        System.exit(1);
                        break;
                    case "1":
                        searchPerson(scanner);
                        break;
                    case "2":
                        printAll();
                        break;
                    default:
                        System.out.println("Incorrect option! Try again.");
                }
            }
        }
    }

    private static void printAll() {
        System.out.println("\n=== List of people ===");
        for (String person : datasets.values()) {
            if (!person.isEmpty()) {
                System.out.println(person);
            }
        }
        System.out.println();
    }

    private static void searchPerson(Scanner scanner) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();

        System.out.println("\nEnter a name or email to search all suitable people.");
        String query = scanner.nextLine();

        switch (strategy) {
            case "ALL":
                searchingStrategy = new SearchingStrategyAll();
                break;
            case "ANY":
                searchingStrategy = new SearchingStrategyAny();
                break;
            case "NONE":
                searchingStrategy = new SearchingStrategyNone();
                break;
            default:
                System.out.println("Unknown strategy");
                return;
        }

        Set<String> result = searchingStrategy.search(invertedIndex, datasets, query);
        if (result.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            for (String dataset : result) {
                System.out.println(dataset);
            }
            System.out.println();
        }
    }

    private static List<String> search(String searchedData) {
        List<String> result = new ArrayList<>();
        if (invertedIndex.containsKey(searchedData)) {
            for (int i : invertedIndex.get(searchedData)) {
                result.add(datasets.get(i));
            }
        }
        return result;
    }
}
