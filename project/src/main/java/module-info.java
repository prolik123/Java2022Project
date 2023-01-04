module proj {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;
    requires transitive org.json;
    //requires org.apache.commons.collections4;
    //requires json.simple;
    requires javafx.graphics;

    opens proj to javafx.fxml;
    exports proj;
}
