package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import javafx.application.Platform;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BookCatalogController implements Initializable {
    public static final String LAYOUT = "/com/innsmouth/library/view/books/book_catalog.fxml";

    public static BookCatalogController createInstance(Stage stage) {
        return new BookCatalogController(sCreateFacade(), stage);
    }

    private static BookRepositoryFacade sCreateFacade() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }

    private final Stage stage;
    private final long selectedBookId;
    private static final long NOTHING_SELECTED = -1;

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
    private Button searchBook_moreBtn;

    public BookCatalogController(BookRepositoryFacade facade, Stage stage) {
        this(facade, stage, NOTHING_SELECTED);
    }

    public BookCatalogController(BookRepositoryFacade facade, Stage stage, long selectedBookId) {
        this.facade = facade;
        this.stage = stage;
        this.selectedBookId = selectedBookId;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI() {
        configIntTextField();
        configTableView();
    }

    private void configIntTextField() {
        searchBook_year.setTextFormatter(new IntTextFormatter());
    }

    private void configTableView() {
        configColumns();
        setSelectedBook();
    }

    private void setSelectedBook() {
        if (selectedBookId == NOTHING_SELECTED) return;
        int selectedItemInd = getSelectedBookIndById();
        Platform.runLater(() -> {
            book_table.requestFocus();
            book_table.getSelectionModel().select(selectedItemInd);
            book_table.getFocusModel().focus(selectedItemInd);
        });

    }

    private int getSelectedBookIndById() {
        List<Book> bookList = book_table.getItems();
        for (int i = 0; i < bookList.size(); i++) {
            Book currBook = bookList.get(i);
            if (selectedBookId == currBook.getBookID()) {
                return i;
            }
        }
        return (int) NOTHING_SELECTED;
    }

    private void configColumns() {
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
        Book query = create();
        List<Book> resultList = facade.search(query);

        bookObservableList.setAll(resultList);
        resultList.forEach(System.out::println);
    }

    private Book create() {
        Book result = new Book();
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
        if (yearText.isEmpty()) {
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
    private void onMoreClicked(ActionEvent actionEvent) {
        try {
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene() throws Exception {
        final long selectedId = getSelectedId();
        if (selectedId == NOTHING_SELECTED) {
            return;
        }

        Scene scene = generateInformBookScene(selectedId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateInformBookScene(final long selectedId) throws Exception {
        Stage stage = (Stage) searchBook_moreBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_menu_inform.fxml"));
        loader.setControllerFactory(t -> createInformBookController(stage, selectedId));

        return new Scene(loader.load());
    }

    private BookMenuInformController createInformBookController(Stage stage, final long selectedId) {
        return new BookMenuInformController(createFacade(), stage, selectedId);
    }

    private BookRepositoryFacade createFacade() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }

    private long getSelectedId() {
        Book selectedBook = book_table.getSelectionModel().getSelectedItem();
        logSelectedBook(selectedBook);

        if (selectedBook == null) return NOTHING_SELECTED;

        return selectedBook.getBookID();
    }


    private void logSelectedBook(Book selectedBook) {
        System.out.println("selectedBook is ");
        System.out.println(selectedBook);
    }
}
