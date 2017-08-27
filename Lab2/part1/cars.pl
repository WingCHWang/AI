name(driver(Name, _, _), Name).
car(driver(_, Car, _), Car).
mileage(driver(_, _, Mileage), Mileage).

lie(driver(_, _, 15)).
lie(driver(_, _, 20)).

permutation([],[]).
permutation(List, [Ele|Permutation]) :-
    select(Ele, List, Rest),
    permutation(Rest, Permutation).

better(driver(_, _, Mileage1), driver(_, _, Mileage2)) :-
    Mileage1 > Mileage2.

person(Person, Name, Car, Mileage, Drivers) :-
    member(driver(Name, Car, Mileage), Drivers) , Person = driver(Name, Car, Mileage).

% judge if two lists are equal, ignore the order of elements.
listeq(List1, List2) :-
    (length(List1, Length) , length(List2, Length)) , not((member(X, List1) , not(member(X, List2)))).

start(Drivers) :-
    Drivers = [driver("George", Car1, Mileage1) ,driver("Doc", Car2, Mileage2) ,driver("Tito", Car3, Mileage3) ,driver("Jimmy", Car4, Mileage4)] ,
    permutation(["Ford", "Toyota", "Chevrolet", "Dodge"], [Car1, Car2, Car3, Car4]) ,
    permutation([15, 20, 25, 30], [Mileage1, Mileage2, Mileage3, Mileage4]) ,
    % Tito said, two situations: (Tito lay, Tito didnt lie).
    person(Tito, "Tito", _, _, Drivers) , person(George, "George", _, _, Drivers) , person(Jimmy, "Jimmy", _, _, Drivers)  , (
        (not(lie(Tito)) , member(driver("Doc", _, 20), Drivers) , better(George, Jimmy)) ;
        (lie(Tito) , not(member(driver("Doc", _, 20), Drivers)) , better(Jimmy, George))
    ),

    % Jimmy said, two situations: (Jimmy lay, Jimmy didnt lie).
    person(Jimmy, "Jimmy", _, _, Drivers), not(member(driver("Doc", "Toyota", _), Drivers)), person(Tito, "Tito", _, _, Drivers) , person(Guy1, _, "Dodge", _, Drivers) , (
        (not(lie(Jimmy)) , better(Tito, Guy1)) ;
        (lie(Jimmy) , better(Guy1, Tito))
    ) ,

    % George said, two situations: (George lay, George didnt lie).
    person(George, "George", _, _, Drivers) ,  person(Guy2, _, "Ford", _, Drivers) ,  person(Guy3, _, _, 20, Drivers) , (
        (not(lie(George)) , mileage(Guy2, 30) , not(car(Guy3, "Chevrolet"))) ;
        (lie(George) , not(mileage(Guy2, 30)) , car(Guy3, "Chevrolet"))
    ) ,

    % Doc said, two situations: (Doc lay, Doc didnt lie).
    person(Doc, "Doc", _, _, Drivers) , (
        (not(lie(Doc)) , mileage(Doc, 20)) ;
        (lie(Doc) , not( mileage(Doc, 20)))
    ).
