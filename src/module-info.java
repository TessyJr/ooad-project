module OOAD_Project {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	
	opens model to javafx.base;
	
	exports main;
}