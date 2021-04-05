package com.almasb.libraryloan;

import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.domain.repository.api.BookRepository;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryLoanApp extends Application {

    private BookRepository buildDAO() {
        return new DerbyBookRepository();
    }

    private BookRepositoryFacade buildModel() {
        return new BookRepositoryFacade(buildDAO());
    }

    private Controller buildController(Stage stage) {
        return new Controller(buildModel(), stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui.fxml"));
        loader.setControllerFactory(t -> buildController(stage));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);

//        BookDAO bookDAO = new DerbyBookDAO();
//
//        bookDAO.setup();
//
//        Library model = new Library(bookDAO);
//        model.addNewBook("TestName1", "John Smith_Jack Smith_Jane Smith", 2010);
//        model.addNewBook("TestName1", "John Smith_Jack Smith_Jane Smith", 2010);
//        model.addNewBook("TestName1", "John Smith_Jack Smith_Jane Smith", 2010);
//        model.addNewBook("TestName1", "John Smith_Jack Smith_Jane Smith", 2010);
//
//        model.addNewBook("TestName2", "John Smith_Jane Smith", 2013);
//        model.addNewBook("TestName2", "John Smith_Jane Smith", 2013);
//        model.addNewBook("TestName2", "John Smith_Jane Smith", 2013);
//        model.addNewBook("TestName2", "John Smith_Jane Smith", 2013);
//
//        model.addNewBook("Name3", "Jane Smith", 2015);
//        model.addNewBook("Name3", "Jane Smith", 2015);
//        model.addNewBook("Name3", "Jane Smith", 2015);
//
//        bookDAO.close();
//        System.exit(0);
    }
}
