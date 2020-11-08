/*
Name: Gary Allen
Date: 10/03/2020
Assignment: Project 1 (main.cpp - a lexical analyzer and recursive descent parser program)
*/

/*arithmetic expressions */
#include <stdio.h>
#include <iostream>
#include <ctype.h>
#include "string"
using namespace std;

/* Global declarations */
/* Variables */
int charClass;
char lexeme[100];
char nextChar;
int lexLen;
int nextToken;
FILE *file_input;

/* Function declarations */
void addChar();
void getChar();
void getNonWhitespaceChar();
void program();
void keyword();
void declare();
void declarident();
void stmts();
void assign();
void expr();
void funcname();
void ident();
int lex();
void getTokenName();

/* Character classes */
#define LETTER 0
#define UNKNOWN 99

/* Token codes */
#define IDENT 11
#define LEFT_PAREN 23
#define RIGHT_PAREN 24
#define LEFT_BRACE 25
#define RIGHT_BRACE 26
#define COMMA 27
#define SEMI_COLON 28
#define ASSIGNMENT_OP 20
#define ADD_OPERATION 21
#define SUBTRACTION_OP 22
#define KEYWORD 29
/******************************************************/
/* program starts here */
int main() {
    /* Open the input data file and process its contents */
    cout<<"Which program would you like to use?"
        <<" Enter a '1' for Test 1 or a '2' for Test 2"<<endl; // prompt user for file selection
    int choice;
    cin>>choice;
    bool checkingInput = true;
        while (checkingInput) { // input validation
            if(choice == 1) {
                checkingInput = false; // stop loop
                file_input = fopen("test1.txt", "r");
                if (file_input == NULL) { //checking for error with file open
                    printf("There was an error with opening the test1.txt file \n");
                } else {
                    getChar();
                    do {
                        program(); // run program through lexical analyzer
                    } while (nextToken != EOF); // loop until the end of file has been reached
                }
            } else if(choice == 2) {
                checkingInput = false; // stop loop
                file_input = fopen("test2.txt", "r");
                if (file_input == NULL) { //checking for error with file open
                    printf("ERROR - cannot open test2.txt \n");
                } else {
                    getChar();
                    do {
                        program(); // run program through lexical analyzer
                    } while (nextToken != EOF); // loop until the end of file has been reached
                }
            } else {
                cout << "There was an error with your file choice please check your input."
                         << endl; // reprompt user for valid input selection
                cout << "Which program would you like to run?"
                        <<" Enter a '1' for Test 1 or a '2' for Test 2"<<endl;
                cin >> choice;
                break;
            }
        }
    }


/*a function to lookup operators and parentheses
and return the token */
int lookup(char ch) {
    switch (ch) {
        case '(': // Left Parenthesis
            addChar();
            nextToken = LEFT_PAREN;
            break;
        case ')': // Right Parenthesis
            addChar();
            nextToken = RIGHT_PAREN;
            break;
        case '+': // Addition Operation
            addChar();
            nextToken = ADD_OPERATION;
            break;
        case '-': // Subtraction Operation
            addChar();
            nextToken = SUBTRACTION_OP;
            break;
        case '=': // Assignment Operation
            addChar();
            nextToken = ASSIGNMENT_OP;
            break;
        case '{': // Left Brace
            addChar();
            nextToken = LEFT_BRACE;
            break;
        case '}': // Right Brace
            addChar();
            nextToken = RIGHT_BRACE;
            break;
        case '\n': // New Line Character
            getChar();
            break;
        case '\t': // Tab Character
            getChar();
            break;
        case ',': // Comma
            addChar();
            nextToken = COMMA;
            break;
        case ';': // Semi-Colon
            addChar();
            nextToken = SEMI_COLON;
            break;
        default: // End of file (EOF)
            addChar();
            nextToken = EOF;
            break;
    }
}

/* a function to add nextChar to lexeme */
void addChar() {
    if (lexLen <= 98) {
        lexeme[lexLen++] = nextChar;
        lexeme[lexLen] = 0;
    }
    else
        printf("The lexeme is too long! \n");
}

/* a function to get the next character of
input and determine its character class */
void getChar() {
    if ((nextChar = getc(file_input)) != EOF) {
        if (isalpha(nextChar))
            charClass = LETTER;
        else charClass = UNKNOWN;
    }
    else
        charClass = EOF;
}

/*a function to get the next non-whitespace character*/
void getNonWhitespaceChar() {
    while (isspace(nextChar))
        getChar();
}

/*a lexical analyzer for arithmetic expressions*/
int lex() {
    lexLen = 0;
    getNonWhitespaceChar();
    switch (charClass) {
        /* Parse identifiers */
        case LETTER:
            addChar();
            getChar();
            while (charClass == LETTER) {
                addChar();
                getChar();
            }

            if(lexeme[0] == 'f') {
                nextToken = KEYWORD;
            } else {
                nextToken = IDENT;
            }
            break;
            /* Parentheses and operators */
        case UNKNOWN:
            lookup(nextChar);
            getChar();
            break;
            /* EOF */
        case EOF:
            nextToken = EOF;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'F';
            lexeme[3] = 0;
            break;
    }
    getTokenName();
}

/*
Parses strings in the language generated by the rule:
<program> -> <keyword> <funcname>() { <declare> <stmts> }
*/
void program() {
    printf("Enter <program>\n");
    keyword();
    funcname();
    lex();
    if (nextToken == LEFT_PAREN) {
        lex();
        if (nextToken == RIGHT_PAREN) {
            lex();
            if (nextToken == LEFT_BRACE) {
                declare();
                stmts();
                lex();
                if (nextToken == RIGHT_BRACE) {
                    printf("Exit <program>\n");
                    lex();
                    printf("The sample program is correct!\n");
                } else {
                    printf("Program Error: Parser found a missing closing brace!\n");
                    exit(1);  
                }
            } else {
                  printf("Program Error: Parser found a missing opening brace!\n");
                  exit(1);
            }
        }  else {
            printf("Program Error: Parser found a missing closing brace!\n");
            exit(1);
        }
    }  else {
        printf("Program Error: Parser found a missing opening brace!\n");
         exit(1);
    }
}

/*
Parses strings in the language generated by the rule:
<keyword> -> float
*/
void keyword() {
    printf("Enter <keyword>\n");
    lex();
    if(nextToken!=KEYWORD) { //validation that the keyword is there
        printf("Program Error: Parser found a missing keyword!\n");
        exit(1);
    }
    printf("Exit <keyword>\n");
}

/*
Parses strings in the language generated by the rule:
<declare> -> float <ident>;
	| float <ident>, <declarident>
*/
void declare() {
    printf("Enter <declare>\n");
    /* Parse the first identifier */
    lex();
    if (nextToken == IDENT || nextToken == KEYWORD) { 
        ident();
        lex();
        if (nextToken == SEMI_COLON) {
            lex();
            printf("Exit <declare>\n");
        }
        else if (nextToken == COMMA) {
            declarident();
        } else { //validation to make sure there is not a missing comma or semicolon in the declaration
            printf("Program Error! The parser found a missing semicolon or comma\n");
            exit(1);
        }
    } 
}

/*
Parses strings in the language generated by the rule:
<declarident> -> <ident>, <declarident>
	| <ident>;
*/
void declarident() {
    printf("Enter <declarident>\n");
    ident();
    lex();
    if (nextToken == COMMA) { 
        declarident();
    }
    printf("Exit <declarident>\n");

}

/*
Parses strings in the language generated by the rule:
<stmts> -> <assign>; <assign>;
*/
void stmts() {
    printf("Enter <stmts>\n");
    assign();
    lex();
    if (nextToken == SEMI_COLON) {
        assign();
        lex();
        if (nextToken == SEMI_COLON) {
            printf("Exit <stmts>\n");
        } else { //validation to make sure there is a semicolon
            printf("Program Error: Parser found a missing semicolon!\n");
            exit(1);
        }
    } else { //validation to make sure there is semicolon
        printf("Program Error: Parser found a missing semicolon!\n");
        exit(1);
    }
}

/*
Parses strings in the language generated by the rule:
<assign> -> <ident> = <expr>
*/
void assign() {
    printf("Enter <assign>\n");
    ident();
    lex();
    if (nextToken == ASSIGNMENT_OP) {
        expr();
        printf("Exit <assign>\n");
    } else { //validation to make sure there an assignment operator
        printf("Program Error: The parser found no assignment operator!\n");
        exit(1);
    }
}

/*
Parses strings in the language generated by the rule:
<expr> -> <ident> {* | /} <expr>
	| <ident>
*/
void expr() {
    printf("Enter <expr>\n");
    /* Parse the first identifier */
    ident();
    lex();
    if(nextToken==IDENT) {
        printf("Program Error: The parser found a missing operator!\n");
        exit(1);
    }
    /* As long as the next token is + or -, get the next token and parse the next term */
    while (nextToken == ADD_OPERATION || nextToken == SUBTRACTION_OP) { //checking here
        bool lastOperator = false;
        if (nextToken == SUBTRACTION_OP) {
            lastOperator = true;
        }
        ident();
        if (!lastOperator) {
          lex();
          if(nextToken==IDENT) {
              printf("Program Error: The parser found a missing operator!\n");
              exit(1);
          }
        }
    }
    printf("Exit <expr>\n");
}

/*
Parses strings in the language generated by the rule:
<funcname> -> <ident>
*/
void funcname() {
    printf("Enter <funcname>\n");

    if (nextToken == IDENT || nextToken == KEYWORD) {
        lex();
        if(nextToken != IDENT) { //validation to make sure there is a function name
            printf("Program Error: The parser found a missing identifier!\n");
            exit(1);
        }
    }
    printf("Exit <funcname>\n");
}

/*
Parses strings in the language generated by the rule:
<ident> -> a <ident> | b <ident> | ... | z <ident> | (Epsilon)
*/
void ident() {
    printf("Enter <ident>\n");
    lex();
    printf("Exit <ident>\n");
}

/*
Exchanges the token code for the token name to be printed
*/
void getTokenName(){
    string tokenName;
    if(nextToken==IDENT ) {
        tokenName = "IDENT";
    } else if(nextToken==KEYWORD) {
        tokenName = "KEYWORD";
    } else if(nextToken==LEFT_PAREN) {
            tokenName = "LEFT_PAREN";
    } else if(nextToken==RIGHT_PAREN) {
        tokenName = "RIGHT_PAREN";
    } else if(nextToken==LEFT_BRACE) {
        tokenName = "LEFT_BRACE";
    } else if(nextToken==RIGHT_BRACE) {
        tokenName = "RIGHT_BRACE";
    } else if(nextToken==COMMA) {
        tokenName = "COMMA";
    } else if(nextToken==SEMI_COLON){
        tokenName = "SEMI_COLON";
    } else if(nextToken==ASSIGNMENT_OP) {
        tokenName = "ASSIGNMENT_OP";
    } else if(nextToken==ADD_OPERATION) {
        tokenName = "ADD_OPERATION";
    } else if(nextToken==SUBTRACTION_OP) {
        tokenName = "SUBTRACTION_OP";
    } else {
        tokenName = "";
    }
    
    if(tokenName.length() > 0) {
        printf("Next token is: %s, Next lexeme is %s\n",
               tokenName.c_str(), lexeme);
    }
}