nation(man(N, _, _, _, _), N).
house(man(_, H, _, _, _), H).
drink(man(_, _, D, _, _), D).
pet(man(_, _, _, P, _), P).
smoke(man(_, _, _, _, S), S).

left(LeftMan, RightMan, Mans) :-
    append(_, [LeftMan, RightMan|_], Mans).
neighbour(Man1, Man2, Mans) :-
    left(Man1, Man2, Mans) ;
    left(Man2, Man1, Mans).
first(Man, [Man, _, _, _, _]).
center(Man, [_, _, Man, _, _]).

start(Mans) :-
    Mans = [man(N1, C1, D1, P1, S1), man(N2, C2, D2, P2, S2), man(N3, C3, D3, P3, S3), man(N4, C4, D4, P4, S4), man(N5, C5, D5, P5, S5)] ,
    % English lives in red house
    member(Man1, Mans) , nation(Man1, "English") , house(Man1, redHouse) ,
    member(Man2, Mans) , nation(Man2, "Swedish") , pet(Man2, dogs) ,
    member(Man3, Mans) , nation(Man3, "Dane") , drink(Man3, tea) ,
    member(Man4Left, Mans) , member(Man4Right, Mans) , left(Man4Left, Man4Right, Mans) , house(Man4Left, greenHouse) , house(Man4Right, whiteHouse) ,
    member(Man5, Mans) , house(Man5, greenHouse) , drink(Man5, coffee) ,
    member(Man6, Mans) , smoke(Man6, pallHall) , pet(Man6, birds) ,
    member(Man7, Mans) , house(Man7, yellowHouse) , smoke(Man7, dunhill) ,
    center(Man8, Mans) , drink(Man8, milk) ,
    first(Man9, Mans) , nation(Man9, "Norwegian") ,
    member(Man10a, Mans) , member(Man10b, Mans) , neighbour(Man10a, Man10b, Mans) , smoke(Man10a, blend) , pet(Man10b, cats) ,
    member(Man11a, Mans) , member(Man11b, Mans) , neighbour(Man11a, Man11b, Mans) , pet(Man11a, horses) , smoke(Man11b, dunhill) ,
    member(Man12, Mans) , smoke(Man12, buleMaster) , drink(Man12, beer) ,
    member(Man13, Mans) , nation(Man13, "German") , smoke(Man13, prince) ,
    member(Man14a, Mans) , member(Man14b, Mans) , neighbour(Man14a, Man14b, Mans) , nation(Man14a, "Norwegian") , house(Man14b , buleHouse) ,
    member(Man15a, Mans) , member(Man15b, Mans) , neighbour(Man15a, Man15b, Mans) , smoke(Man15a, blend) , drink(Man15b, water) ,
    member(Man16, Mans) , pet(Man16, fish).
