#!/bin/bash

echo "[1] Deleting old class files..."
if [ -d "out" ]; then
    rm -rf out
    echo "Deleted previous 'out' directory."
else
    echo "'out' directory not found, skipping delete."
fi

echo "[2] Compiling Java files..."
mkdir -p out
javac -d out \
src/endgame/*.java \
src/gui/*.java \
src/actions/*.java \
src/entities/*.java \
src/items/*.java \
src/map/*.java \
src/tsw/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "[3] Copying resources..."
cp -R res out/res

echo "[4] Running program..."
java -cp out src.gui.Main
