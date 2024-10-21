import java.util.*;

public class LaptopStore {
    public static void main(String[] args) {
        Set<Laptop> laptops = new HashSet<>(Arrays.asList(
            new Laptop(8, 512, "Windows 10", "Black"),
            new Laptop(16, 1024, "Windows 11", "Silver"),
            new Laptop(8, 256, "Ubuntu", "Black"),
            new Laptop(32, 1024, "macOS", "Gray"),
            new Laptop(16, 512, "Windows 10", "White")
        ));

        Map<Integer, String> criteria = Map.of(
            1, "ОЗУ",
            2, "Объем ЖД",
            3, "Операционная система",
            4, "Цвет"
        );

        Map<String, Object> filters = getFiltersFromUser(criteria);
        filterLaptops(laptops, filters);
    }

    // Метод для получения фильтров от пользователя
    public static Map<String, Object> getFiltersFromUser(Map<Integer, String> criteria) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> filters = new HashMap<>();
        boolean continueFiltering = true;

        while (continueFiltering) {
            System.out.println("Введите цифру, соответствующую необходимому критерию:");
            criteria.forEach((key, value) -> System.out.println(key + " - " + value));

            int criterion = getValidInt(scanner, "Выберите критерий: ");
            switch (criterion) {
                case 1 -> filters.put("ram", getValidInt(scanner, "Введите минимальное значение ОЗУ (в ГБ): "));
                case 2 -> filters.put("hdd", getValidInt(scanner, "Введите минимальный объем ЖД (в ГБ): "));
                case 3 -> {
                    System.out.print("Введите операционную систему: ");
                    filters.put("os", scanner.nextLine());
                }
                case 4 -> {
                    System.out.print("Введите цвет: ");
                    filters.put("color", scanner.nextLine());
                }
                default -> System.out.println("Некорректный критерий. Попробуйте снова.");
            }

            System.out.print("Хотите добавить еще один критерий? (да/нет): ");
            continueFiltering = scanner.nextLine().equalsIgnoreCase("да");
        }

        return filters;
    }

    // Метод для фильтрации ноутбуков по критериям
    public static void filterLaptops(Set<Laptop> laptops, Map<String, Object> filters) {
        List<Laptop> filteredLaptops = new ArrayList<>();

        for (Laptop laptop : laptops) {
            boolean matches = true;

            if (filters.containsKey("ram") && laptop.getRam() < (int) filters.get("ram")) {
                matches = false;
            }
            if (filters.containsKey("hdd") && laptop.getHdd() < (int) filters.get("hdd")) {
                matches = false;
            }
            if (filters.containsKey("os") && 
                !laptop.getOs().equalsIgnoreCase((String) filters.get("os"))) {
                matches = false;
            }
            if (filters.containsKey("color") && 
                !laptop.getColor().equalsIgnoreCase((String) filters.get("color"))) {
                matches = false;
            }

            if (matches) {
                filteredLaptops.add(laptop);
            }
        }

        if (filteredLaptops.isEmpty()) {
            System.out.println("Нет ноутбуков, соответствующих выбранным критериям.");
        } else {
            System.out.println("Найденные ноутбуки:");
            filteredLaptops.forEach(System.out::println);
        }
    }

    // Метод для безопасного ввода целого числа
    public static int getValidInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }
}
