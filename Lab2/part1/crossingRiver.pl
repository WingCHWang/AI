% state is represented as a list that contians the items at the original side.

% initial state and final state.
initial([policeman, criminal, father, mother, son1, son2, daughter1, daughter2, boat]).
final([]).

% remove an item from a state and get a new state, add is similar.
removeItem(X, [X|Rest], Rest).
removeItem(X, [Y|Rest], [Y|Rest2]) :-
    removeItem(X, Rest, Rest2).

addItem(X, List, [X|List]) :-
    not(member(X, List)).

% judge if two lists are equal, ignore the order of elements.
listeq(List1, List2) :-
    (length(List1, Length) , length(List2, Length)) , not((member(X, List1) , not(member(X, List2)))).

% judge if a list is an element of a List of lists.
contain(List, Path) :-
    member(X, Path) , listeq(X, List).

% go through a list and print elements of it.
printlist(List) :-
    not( (member(X, List) , writeln(X) , not(member(X, List))) ).

% judge a state is unsafe or not.
unsafe(State) :-
    member(criminal, State) , not(member(policeman, State)) , (member(X, [father, mother, son1, son2, daughter1, daughter2]) , member(X, State)).
unsafe(State) :-
    member(father, State) , not(member(mother, State)) , (member(daughter1, State) ; member(daughter2, State)).
unsafe(State) :-
    member(mother, State) , not(member(father, State)) , (member(son1, State) ; member(son2, State)).
unsafe(State) :-
    member(policeman, State) , not(member(criminal, State)) ,  (member(X, [father, mother, son1, son2, daughter1, daughter2]) , not(member(X, State))).
unsafe(State) :-
    not(member(father, State)) , member(mother, State) , (not(member(daughter1, State)) ; not(member(daughter2, State))).
unsafe(State) :-
    not(member(mother, State)) , member(father, State) , (not(member(son1, State)) ; not(member(son2, State))).

move(State, NewState) :-
    removeItem(boat, State, TempState) , removeItem(policeman, TempState, NewState).
move(State, NewState) :-
    removeItem(boat, State, TempState) , removeItem(father, TempState, NewState).
move(State, NewState) :-
    removeItem(boat, State, TempState) , removeItem(mother, TempState, NewState).
move(State, NewState) :-
    removeItem(boat, State, TempState1) , removeItem(policeman, TempState1, TempState2) , (member(X, TempState2), removeItem(X, TempState2, NewState)).
move(State, NewState) :-
    removeItem(boat, State, TempState1) , removeItem(father, TempState1, TempState2) , (member(X, [mother, son1, son2]), removeItem(X, TempState2, NewState)).
move(State, NewState) :-
    removeItem(boat, State, TempState1) , removeItem(mother, TempState1, TempState2) , (member(X, [daughter1, daughter2]), removeItem(X, TempState2, NewState)).
move(State, NewState) :-
    addItem(boat, State, TempState) , addItem(policeman, TempState, NewState).
move(State, NewState) :-
    addItem(boat, State, TempState) , addItem(father, TempState, NewState).
move(State, NewState) :-
    addItem(boat, State, TempState) , addItem(mother, TempState, NewState).
move(State, NewState) :-
    addItem(boat, State, TempState1) , addItem(policeman, TempState1, TempState2) , (member(X, [criminal, father, mother, son1, son2, daughter1, daughter2]) , addItem(X, TempState2, NewState)).
move(State, NewState) :-
    addItem(boat, State, TempState1) , addItem(father, TempState1, TempState2) , (member(X, [mother, son1, son2]), addItem(X, TempState2, NewState)).
move(State, NewState) :-
    addItem(boat, State, TempState1) , addItem(mother, TempState1, TempState2) , (member(X, [daughter1, daughter2]), addItem(X, TempState2, NewState)).

start :-
    bagof(_, rs, _).

rs :-
    initial(State) ,step(State, [State]).
step(State, Path) :-
    final(State) , writeln("Path:") , printlist(Path) , writeln("").
step(State, Path) :-
    move(State, NewState) , not(contain(NewState, Path)) , not(unsafe(NewState)), append(Path, [NewState] , NewPath) , step(NewState, NewPath).
