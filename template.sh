cp src/main/java/Template.java src/main/java/"$1".java

sed -i s/Template/"$1"/g src/main/java/"$1".java

vim src/main/java/"$1".java
