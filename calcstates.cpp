/*
*	Calculates all possible states 
*	in a game of tic-tac-toe
*	Stores data in json format
*
*	The CLOSE states represent when one or both
*	sides can win with a single move
*/

#include <iostream>
#include <fstream>
#include <cmath>
#include <string>

using namespace std;

enum State {
	UNDECIDED,
	DRAW,
	WIN_CROSS,
	WIN_NOUGHT,
	CLOSE_CROSS,
	CLOSE_NOUGHT,
	CLOSE_BOTH
};

void findStates(State states[]);
int getBoardBitMask(int board[]);
State getBoardState(int board[], bool doCheckMoves);
State checkMoves(int board[]);
void createJSON(State states[]);
string stateToString(State s);

int main(){

	State states[19683];
	findStates(states);
	createJSON(states);
	
}

void findStates(State states[]){
	for(int i = 0; i < 19683; i++){
		states[i] = UNDECIDED;
	}
	
	//Easier to go over 4^9 states and skip those invalid ones
	for(int n = 0; n < 262144; n++){
		bool valid = (n & 3) < 3;
		valid &= ((n >>  2) & 3) < 3;
		valid &= ((n >>  4) & 3) < 3;
		valid &= ((n >>  6) & 3) < 3;
		valid &= ((n >>  8) & 3) < 3;
		valid &= ((n >> 10) & 3) < 3;
		valid &= ((n >> 12) & 3) < 3;
		valid &= ((n >> 14) & 3) < 3;
		valid &= ((n >> 16) & 3) < 3;
		
		if(!valid){
			continue;
		} else {
			int i = n;
			int j = 0;
			
			//0 empty, 1 crosses, 2 noughts
			int board[9];
			
			while(j < 9){
				board[j] = (i & 3);
				i >>= 2;
				j++;
			}
			states[getBoardBitMask(board)] = getBoardState(board, true);
		}
	}
}

int getBoardBitMask(int board[]){
	int mask = 0;
	for(int i = 0; i < 9; i++){
		mask += board[i]*pow(3,i);
	}
	return mask;
}

State getBoardState(int board[], bool doCheckMoves){
	//Check columns
	for(int i = 0; i < 3; i++){
		if(board[i] == board[i + 3] && board[i] == board[i + 6]){
			if(board[i] == 1){
				return WIN_CROSS;
			} else if(board[i] == 2){
				return WIN_NOUGHT;
			}
		}
	}
	
	//Check rows
	for(int i = 0; i < 3; i++){
		if(board[i*3] == board[i*3 + 1] && board[i*3] == board[i*3 + 2]){
			if(board[i*3] == 1){
				return WIN_CROSS;
			} else if(board[i*3] == 2){
				return WIN_NOUGHT;
			}
		}
	}
	
	//Check diagonal (/)
	if(board[0] == board[4] && board[0] == board[8]){
		if(board[0] == 1){
			return WIN_CROSS;
		} else if(board[0] == 2){
			return WIN_NOUGHT;
		}
	}
	
	//Check diagonal (\)
	if(board[2] == board[4] && board[2] == board[6]){
		if(board[2] == 1){
			return WIN_CROSS;
		} else if(board[2] == 2){
			return WIN_NOUGHT;
		}
	}
	
	//Check for a draw
	bool draw = true;
	for(int i = 0; i < 9; i++){
		if(board[i] == 0){
			draw = false;
			break;
		}
	}
	
	if(draw){
		return DRAW;
	}
	
	if(doCheckMoves){
		//Now check for possible winning moves
		return checkMoves(board);
	} else {
		return UNDECIDED;
	}
	
}

State checkMoves(int board[]){
	bool crossWin = false;
	bool noughtWin = false;
	
	//Check crosses moves
	for(int i = 0; i < 9; i++){
		if(board[i] == 0){
			int newBoard[9];
			for(int j = 0; j < 9; j++){
				if(i == j){
					newBoard[j] = 1; //Add in a move for crosses
				} else {
					newBoard[j] = board[j];
				}
			}
			if(getBoardState(newBoard, false) == WIN_CROSS){
				crossWin = true;
				break;
			}
		}
	}
	
	//Check noughts moves
	for(int i = 0; i < 9; i++){
		if(board[i] == 0){
			int newBoard[9];
			for(int j = 0; j < 9; j++){
				if(i == j){
					newBoard[j] = 2; //Add in a move for noughts
				} else {
					newBoard[j] = board[j];
				}
			}
			if(getBoardState(newBoard, false) == WIN_NOUGHT){
				noughtWin = true;
				break;
			}
		}
	}
	
	if(crossWin){
		if(noughtWin){
			return CLOSE_BOTH;
		} else {
			return CLOSE_CROSS;
		}
	} else {
		if(noughtWin){
			return CLOSE_NOUGHT;
		} else {
			return UNDECIDED;
		}
	}
}

void createJSON(State states[]){
	fstream file;
	file.open("states.json", fstream::out);
	
	file << "{" << endl;
	file << "states: [" << endl;
	for(int i = 0; i < 19683; i++){
		file << "\t" << stateToString(states[i]);
		if(i != 19682){
			file << ",";
		}
		file << endl;
	}
	file << "]" << endl;
	file << "}" << endl;
	
	file.close();
}

string stateToString(State s){
	switch(s) {
		case UNDECIDED:
			return "UNDECIDED";
		case DRAW:
			return "DRAW";
		case WIN_CROSS:
			return "WIN_CROSS";
		case WIN_NOUGHT:
			return "WIN_NOUGHT";
		case CLOSE_CROSS:
			return "CLOSE_CROSS";
		case CLOSE_NOUGHT:
			return "CLOSE_NOUGHT";
		case CLOSE_BOTH:
			return "CLOSE_BOTH";	
	}
	
	return "ERR";
} 


