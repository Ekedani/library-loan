package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.data.dataobject.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookCatalogController implements Initializable {

    @FXML
    private TextField searchBook_genre;

    @FXML
    private TextField searchBook_title;

    @FXML
    private TextField searchBook_author;

    @FXML
    private TextField searchBook_year;

    @FXML
    private Button searchBook_searchBtn;

    private final BookRepositoryFacade facade;

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

    ObservableList<Book> bookObservableList = FXCollections.observableArrayList();

    public BookCatalogController(BookRepositoryFacade facade, Stage stage) {
        this.facade = facade;
        stage.setOnCloseRequest(e -> facade.close());
    }

    public BookCatalogController(BookRepositoryFacade facade){
        this.facade = facade;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configTableView();
        populateTableViewWithAllBooks();
    }

    public void onSearch(/*ActionEvent actionEvent*/) {
        logSearchStatements();
        queryBooks();
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

    private void configTableView() {
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
    }

    private void logSearchStatements() {
        System.out.println("title: " + getTitleText());
        System.out.println("author: " + getAuthorText());
        System.out.println("year: " + getYearText());
        System.out.println("genre: " + getGenreText());
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
        return 0; //todo implement it
    }

    private String getGenreText() {
        return searchBook_genre.getText();
    }

}
