cp src/main/java/"$1".java src/main/java/"$2".java

sed -i s/"$1"/"$2"/g src/main/java/"$2".java

vim src/main/java/"$2".java
