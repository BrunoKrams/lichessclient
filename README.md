# Voice Control for lichess.org
This is a project that allows you to control your chess games on lichess using voice commands.
It uses the OpenAI API to process your voice commands and translate them into moves on the chessboard. To narrow down
the number of possible moves, the current position of the chess game is read from lichess and passed to [Chesslib|https://github.com/bhlangonijr/chesslib].

# Usage
To use it you must have a lichess account and an OpenAI API-Key. 
The OpenAI API-Key must be provided as an environment variable named "OPENAI_API_KEY".

# Hints
Currently only commands in german language are supported.
To make a move you must provide the piece, the origin square and the target square. E.g.: "König a4 auf a3".
