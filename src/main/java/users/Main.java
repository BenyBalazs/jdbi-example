package users;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main  {

    public static void main(String[] args) {

        User user2 = User.builder()
                .username("007")
                .password("password")
                .name("James Bond")
                .email("asd@asd.com")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1920-11-11"))
                .enabled(true)
                .build();

        User user1 = User.builder()
                .username("069")
                .password("password")
                .name("GenericLánynév GenericName")
                .email("asd2@asdasd.com")
                .gender(User.Gender.FEMALE)
                .dob(LocalDate.parse("1921-11-11"))
                .enabled(false)
                .build();

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle handle = jdbi.open()){
            System.out.println("Hello");
            UserDao userDao = handle.attach(UserDao.class);
            userDao.createTable();
            userDao.insertUser(user1);
            userDao.insertUser(user2);
            System.out.println("Listing everyone");
            System.out.println(userDao.list());
            System.out.println("---------------------");
            System.out.println("Finding Id: 1");
            System.out.println(userDao.findById(user1.getId()));
            System.out.println("---------------------");
            System.out.println("Find by username: 069");
            System.out.println(userDao.findByUsername("069"));
            System.out.println("---------------------");
            System.out.println("Deleting user 1");
            userDao.delete(user1);
            System.out.println("---------------------");
            System.out.println("Result");
            System.out.println(userDao.list());

        }

    }

}
