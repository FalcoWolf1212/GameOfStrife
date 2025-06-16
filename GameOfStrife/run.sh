#!/bin/bash

# Optional: Set path to external libraries
LIBS="libs/json-20250107.jar"

# Run the JAR with the external library
java -cp "GameOfStrife.jar:$LIBS" gui.GameofStrife
