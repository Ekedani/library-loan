package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.ResourceBundle;

public class BookCatalogController implements Initializable {
    private final Stage stage;

    @FXML
    private TextField searchBook_genre;
    @FXML
    private TextField searchBook_title;
    @FXML
    private TextField searchBook_author;
    @FXML
    private TextField searchBook_year;

    @FXML
    private TableView<Book> book_table;
    @FXML
    private TableColumn<Book, String> col_title;
    @FXML
    private TableColumn<Book, String> col_genre;
    @FXML
    private TableColumn<Book, String> col_author;
    @FXML
    private TableColumn<Book, Integer> col_year;

    private final BookRepositoryFacade facade;

    private final ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
    @FXML
    private Button searchBook_nextScreenBtn;

    public BookCatalogController(BookRepositoryFacade facade, Stage stage) {
        this.facade = facade;
        this.stage = stage;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        populateTableViewWithAllBooks();
    }

    private void configUI() {
        configIntTextField();
        configTableView();
    }

    private void configIntTextField() {
        searchBook_year.setTextFormatter(new IntTextFormatter());
    }

    private void configTableView() {
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
    }

    public void onSearch(/*ActionEvent actionEvent*/) {
        logSearchStatements();
        queryBooks();
    }

    public void onShowAll(/*ActionEvent actionEvent*/) {
        populateTableViewWithAllBooks();
    }

    private void queryBooks() {
        BookQuery query = createQuery();
        List<Book> resultList = facade.search(query);

        bookObservableList.setAll(resultList);
        resultList.forEach(System.out::println);
    }

    private BookQuery createQuery() {
        BookQuery result = new BookQuery();
        result.setAuthor(getAuthorText());
        result.setTitle(getTitleText());
        result.setPublishYear(getYearText());
        result.setGenre(getGenreText());

        return result;
    }

    private void populateTableViewWithAllBooks() {
        List<Book> allBooks = facade.findAll();
        bookObservableList.setAll(allBooks);
        book_table.setItems(bookObservableList);
    }

    private String getTitleText() {
        return searchBook_title.getText();
    }

    private String getAuthorText() {
        return searchBook_author.getText();
    }

    private int getYearText() {
        String yearText = searchBook_year.getText();
        if (yearText.isEmpty()){
            return 0;
        }
        return Integer.parseInt(yearText);
    }

    private String getGenreText() {
        return searchBook_genre.getText();
    }

    private void logSearchStatements() {
        System.out.println("title: " + getTitleText());
        System.out.println("author: " + getAuthorText());
        System.out.println("year: " + getYearText());
        System.out.println("genre: " + getGenreText());
    }

    @FXML
    private void onNextScreen(ActionEvent actionEvent) {
        try {
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene() throws Exception {
        Scene scene = generateScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateScene() throws Exception {
        Stage stage = (Stage) searchBook_nextScreenBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/add_book.fxml"));
        loader.setControllerFactory(t -> createController(stage));

        return new Scene(loader.load());
    }

    private AddBookController createController(Stage stage) {
        return new AddBookController(createLibrary(), stage);
    }

    private BookRepositoryFacade createLibrary() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }
}
