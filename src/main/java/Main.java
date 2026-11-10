import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static void main(String[] args) {
        // Загрусзка фактов окошках
        CatFactLoader catFactLoader = new CatFactLoader(URL);
        boolean isLoaded = catFactLoader.load();
        if (!isLoaded) {
            System.out.println("Не удалось загрузить факты");
        }
        List<CatFact> catFacts = catFactLoader.getCatFacts();

        // выбор фактов с upvotes не null
        List<CatFact> catFactsWithUpvotes = catFacts.stream()
                .filter(cat -> cat.getUpvotes() != null)
                .collect(Collectors.toList());
        System.out.println("Факты о котах с upvotes != null:");
        for(int i = 0; i < catFactsWithUpvotes.size(); i++) {
            System.out.println(i + ": " + catFactsWithUpvotes.get(i) + "\n");
        }
    }
}
