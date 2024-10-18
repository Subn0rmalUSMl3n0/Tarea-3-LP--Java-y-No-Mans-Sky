all: Game.jar

#el ejecutable
Game.jar: $(OUT_DIR)/Game/NoJavaSky.class
	jar cfe Game.jar Game.NoJavaSky -C $(OUT_DIR) .

#busca NoJavaSky.java y las subcarpetas de Game y compila
$(OUT_DIR)/Game/NoJavaSky.class: $(FILES)
	javac -g -sourcepath $(SRC_DIR) -d $(OUT_DIR) -classpath $(CLASSPATH) $(FILES)

#borra Game.jar
clean:
	rm -rf $(OUT_DIR) Game.jar

#corre el archivo
run: Game.jar
	java -jar Game.jar

#definicion de variables
SRC_DIR = Game
FILES = $(shell find $(SRC_DIR) -name "*.java")
CLASSPATH = $(SRC_DIR)
OUT_DIR = bin