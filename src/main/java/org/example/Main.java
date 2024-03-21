package org.example;

import org.example.dao.*;
import org.example.enums.Feature;
import org.example.enums.Rating;
import org.example.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {
    private final SessionFactory sessionFactory;

    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FIlmTextDAO fIlmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public Main() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FIlmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addProperties(properties)
                .buildSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        fIlmTextDAO = new FIlmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();

        Customer customer = main.createCustomer();

        main.customerReturnFilmToStore();

        main.customerRentInventory(customer);

        main.newFilmWasMade();
    }

    private void newFilmWasMade() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0, 20).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 20);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setCategories(new HashSet<>(categories));
            film.setLanguage(language);
            film.setOriginalLanguage(language);
            film.setRating(Rating.PG13);
            film.setSpecialFeatures(Set.of(Feature.COMMENTARIES, Feature.DELETED_SCENES));
            film.setLength((short) 54);
            film.setReplacementCost(BigDecimal.valueOf(55.33));
            film.setRentalRate(BigDecimal.ZERO);
            film.setDescription("bla bla");
            film.setTitle("Bla");
            film.setRentalDuration((byte) 50);
            film.setYear(Year.now());
            filmDAO.save(film);

            FIlmText fIlmText = new FIlmText();
            fIlmText.setDescription("bla bla");
            fIlmText.setFilm(film);
            fIlmText.setTitle("Bla");
            fIlmText.setId(film.getId());
            fIlmTextDAO.save(fIlmText);

            session.getTransaction().commit();
        }
    }

    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDAO.getFirstAvailableFilm();
            Inventory inventory = new Inventory();
            Store store = storeDAO.getItems(0, 1).get(0);
            inventory.setStore(store);
            inventory.setFilm(film);
            inventoryDAO.save(inventory);

            Staff staff = store.getStaff();
            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(44.77));
            payment.setRental(rental);
            payment.setStaff(staff);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    private void customerReturnFilmToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getAnyItem();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("Kursk");
            Address address = new Address();
            address.setAddress("Lucky street, 111");
            address.setCity(city);
            address.setPhone("123456789");
            address.setDistrict("District");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setStore(store);
            customer.setActive(true);
            customer.setFirstName("Iliya");
            customer.setLastName("Pupkin");
            Customer save = customerDAO.save(customer);

            session.getTransaction().commit();
            return save;
        }
    }
}
