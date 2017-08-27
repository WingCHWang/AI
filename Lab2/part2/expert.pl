% 用1,2,3,4代表不同时期，分别对应五四运动之前，五四运动到建国，建国道文革，文革之后
book(Title, Author, Period, Topic, Style, Character, Length, Type, Background, Ending).

title(Title, book(Title, _, _, _, _, _, _, _, _, _)).

author(Author, book(_, Author, _, _, _, _, _, _, _, _)).

period(Period, book(_, _, Period, _, _, _, _, _, _, _)).

topic(Topic, book(_, _, _, Topic, _, _, _, _, _, _)).

style(Style, book(_, _, _, _, Style, _, _, _, _, _)).

character(Character, book(_, _, _, _, _, Character, _, _, _, _)).

background(Background, book(_, _, _, _, _, _, _, _, Background, _)).

topicOf(Topic, book(_, _, _, TopicList, _, _, _, _, _, _)) :-
    length(TopicList, Length) ,
    (Length = 0 ;
    (not(Length = 0) , member(Topic, TopicList))) .

styleOf(Style, book(_, _, _, _, StyleList, _, _, _, _, _)) :-
    length(StyleList, Length) ,
    (Length = 0 ;
    (not(Length = 0) , member(Style, StyleList))) .


characterOf(Character, book(_, _, _, _, _, CharacterList, _, _, _, _)) :-
    length(CharacterList, Length) ,
    (Length = 0 ;
    (not(Length = 0) , member(Character, CharacterList))) .

bookLength(Length, book(_, _, _, _, _, _, Length, _, _, _)).

type(Type, book(_, _, _, _, _, _, _, Type, _, _)).

backgroundOf(Background, book(_, _, _, _, _, _, _, _, BackgroundList, _)) :-
    length(BackgroundList, Length) ,
    (Length = 0 ;
    (not(Length = 0) , member(Background, BackgroundList))) .

ending(Ending, book(_, _, _, _, _, _, _, _, _, Ending)).



other(Other, book(_, _, _, _, _, _, _, _, _, _, Other)).

otherOf(Other, book(_, _, _, _, _, _, _, _, OtherList, _, _)) :-
    length(OtherList, Length) ,
    (Length = 0 ;
    (not(Length = 0) , member(Other, OtherList))) .

period_input(Input, Book) :-
    (Input = 0, period(_, Book)) ;
    (Input = 1, period('before 1919', Book)) ;
    (Input = 2, period('1919-1949', Book)) ;
    (Input = 3, period('1949-1976', Book)) ;
    (Input = 4, period('after 1976', Book)).

bookLength_input(Num, Book) :-
    (Num = 0 , bookLength(_, Book)) ;
    (Num = 1 , bookLength(short, Book)) ;
    (Num = 2 , bookLength(medium, Book)) ;
    (Num = 3 , bookLength(long, Book)) .

type_input(Num, Book) :-
    (Num = 0 , type(_, Book)) ;
    (Num = 1 , type(chapter, Book)) ;
    (Num = 2 , type(collection, Book)) ;
    (Num = 3 , type(dictionary, Book)) ;
    (Num = 4 , type(note, Book)) ;
    (Num = 5 , type(diary, Book)) .

ending_input(Num, Book) :-
    (Num = 0 , ending(_, Book)) ;
    (Num = 1 , ending(tragedy, Book)) ;
    (Num = 2 , ending(_, Book)) .

style_input(Input, Book) :-
    (Input = 0, styleOf(_, Book)) ;
    (not(Input = 0) , styleOf(Input, Book)) .

topic_input(Input, Book) :-
    (Input = 0, topicOf(_, Book)) ;
    (not(Input =0) , topicOf(Input, Book)) .

background_input(Input, Book) :-
    (Input = 0, backgroundOf(_, Book)) ;
    (not(Input =0) , backgroundOf(Input, Book)) .

character_input(Input, Book) :-
    (Input = 0, characterOf(_, Book)) ;
    (not(Input =0) , characterOf(Input, Book)) .

% go through a list and print elements of it.
printlist(List) :-
    length(List, Length) ,
    (Length = 0 ;
    (not(Length = 0) , not( (member(X, List) , write(X) ,  write(',') , not(member(X, List))) ))).

bookInformation(Book) :-
    writeln('\nbook information:') ,
    title(Title, Book) , write('title:  ') , writeln(Title) ,
    author(Author, Book) , write('author:  ') , writeln(Author) ,
    period(Period, Book) , write('writing period:  ') , writeln(Period) ,
    type(Type, Book) , write('type:  ') , writeln(Type) ,
    bookLength(Length, Book) , write('length:  ') , writeln(Length) ,
    topic(Topic, Book) , write('topic:  ') , printlist(Topic) , write('\n') ,
    character(Character, Book) , write('main characters:  ') , printlist(Character) , write('\n') ,
    style(Style, Book) , write('writing style:  ') , printlist(Style) , write('\n') ,
    ending(Ending, Book) , write('ending:  ') , writeln(Ending) .



bookData(Data) :-
    Data = [
    book('Na Han', 'Lu Xun', '1919-1949', ['nation', 'society', 'change', 'system'], ['satire', 'exaggeration',  'plain'], ['intellectual','worker','official'], short, collection, ['Wu Si', 'Xin Hai Revolution'], 0) ,
    book('Bian Cheng', 'Shen Congwen', '1919-1949', ['love', 'family affection', 'local'], ['lyrical', 'essay',  'simple'], ['boatman', 'youth'], medium, chapter, ['30s', 'Xiang Xi'], tragedy) ,
    book('Luo Tuo Xiang Zi', 'Lao She', '1919-1949', ['society', 'lower class', 'system'], ['simple'], ['driver'], long, chapter, ['20s', 'Peking'], tragedy) ,
    book('Chuan Qi', 'Zhang Ailing', '1919-1949', ['amphoteric', 'marriage', 'family affection', 'society'], ['reveal', 'special viewpoint', 'cold'], ['uper-middle class'], medium, collection, ['Hong Kong', 'Shanghai'], 0),
    book('Wei Cheng', 'Qian Zhongshu', '1919-1949', ['love', 'marriage', 'reality'], ['satire', 'refining'], ['intellectual'], long, chapter, ['Anti-Japanese War'], 0),
    book('Zi Ye', 'Mao Dun', '1919-1949', ['society', 'struggle', 'class'], ['clear', 'complex', 'realistic'], ['capitalists'], long, chapter, ['30s', 'Shanghai'], 0),
    book('Jia', 'Ba Jin', '1919-1949', ['family', 'system', 'democracy', 'love'], ['accuse', 'simple'], ['youth', 'feudal parents'], long, chapter, ['Wu Si', 'Chengdu'], 0),
    book('Hu Lan He Zhuan', 'Xiao Hong', '1919-1949', ['local', 'kindred', 'society'], ['essay', 'lyrical'], ['everyman'], long, chapter, ['20s'], 0),
    book('Lao Can You Ji', 'Liu e', 'before 1919', ['bureaucracy', 'reality', 'society'], ['accuse'], ['official'], medium, chapter, ['Qing'], 0),
    book('Han Ye', 'Ba Jin', '1919-1949', ['ideal', 'reality', 'family affection', 'love'], ['realistic', 'psychological description'], ['intellectual'], long, chapter, ['Anti-Japanese War'], tragedy),
    book('Pang Huang', 'Lu Xun', '1919-1949', ['fate', 'society'], ['satire', 'exaggeration',  'plain'], ['intellectual', 'farmer'], short, collection, ['Wu Si'], 0),
    book('Guan Chang Xian Xing Ji', 'Li Boyuan', 'before 1919', ['bureaucracy', 'reality', 'society'], ['accuse'], ['official'], long, chapter, ['Qing'], 0),
    book('Cai Zhu Di Er Nv Men', 'Lu Ling', '1919-1949', ['family', 'struggle','bourgeois class'], ['profound', 'detail'], ['youth','intellectual'], long, chapter, ['wealthy family'], 0),
    book('Jiang Jun Zu', 'Chen Yinzhen', '1949-1976', ['love', 'life', 'death'], ['satire', 'cold'], ['everyman'], short, chapter, ['Taiwan'], tragedy),
    book('Chen Lun', 'Yu Dafu', '1919-1949', ['time', 'experience'], ['lyrical', 'autobiography'], ['self'], short, collection, ['Wu Si', 'Anti-Japanese War'], 0),
    book('Si Shui Wei Lan', 'Li Jieren', '1919-1949', ['reality', 'kindred', 'society'], ['simple','intricate'], ['man','woman'], long, chapter, ['Xin Zhou'], 0),
    book('Hong Gao Liang', 'Mo Yan', 'after 1976', ['history', 'humanity', 'wild', 'resist'], ['first person', 'barbarous'], ['hero', 'bandit'], long, chapter, ['Anti-Japanese War'], 0),
    book('Xiao Er Hei Jie Hun', 'Zhao Shuli', '1919-1949', ['love', 'marriage', 'local', 'struggle'], ['dialect', 'story'], ['farmer'], short, chapter, ['Anti-Japanese War', 'countryside'], 0),
    book('Qi Wang', 'A Cheng', 'after 1976', ['spirit', 'aesthetic ', 'philosophy'], ['first person'], ['educated youth'], short, chapter, ['Wen Ge'], 0),
    book('Jia Bian', 'Wang Wenxing', '1949-1976', ['family', 'reality'], ['detail', 'exact'], ['father', 'son'], long, chapter, ['Taiwan'], 0),
    book('Ma Qiao Ci Dian', 'Han Shaogong', 'after 1976', ['nation', 'local', 'tradition'], ['dialect', 'story'], ['entry'], long, dictionary, ['countryside'], 0),
    book('Ya Xi Ya Gu Er', 'Wu Zhuoliu', '1919-1949', ['history'], ['simple','Janpanese'], ['Taiwan history'], long, chapter, ['Taiwan', 'Japan'], 0),
    book('Ban Sheng Yuan', 'Zhang Ailing', '1949-1976', ['marriage', 'humanity', 'society', 'female'], ['nature'], ['youth'], long, chapter, ['Shanghai'], 0),
    book('Si Shi Tong Tang', 'Lao She', '1919-1949', ['family', 'society', 'struggle','classes'], ['simple', 'small viewpoint'], ['family'], long, chapter, ['Anti-Japanese War', 'Peking'], 0),
    book('Hu Xueyan', 'Gao Yang', 'after 1976', ['history', 'biography'], ['story'], ['Hu Xueyan'], long, chapter, ['Qing'], 0),
    book('Ti Xiao Yin Yuan', 'Zhang Henshui', '1919-1949', ['love', 'society'], ['legend', 'intricate'], ['youth'], long, chapter, ['Beiyang Jun Fa'], tragedy),
    book('Er Zi De Da Wan Ou', 'Huang Chun Ming', '1949-1976', ['local', 'society'], ['story', 'satire', 'realistic', 'humoe'], ['father'], medium, chapter, ['Taiwan'], 0),
    book('She Diao Ying Xiong Zhuan', 'Jin Yong', '1949-1976', ['swordsman'], ['story', 'intricate'], ['hero'], long, chapter, ['Song'], 0),
    book('Sha Fei Nv Shi Ri Ji', 'Ding Ling', '1919-1949', ['love', 'female', 'self', 'rebel'], ['sensitive'], ['woman'], medium, diary, ['Wu Si', 'Peking'], tragedy),
    book('Lu Ding Ji', 'Jin Yong', 'after 1976', ['swordsman'], ['story', 'intricate'], ['hero'], long, chapter, ['Qing'], 0),
    book('Lie Hai Hua', 'Zeng Pu', 'before 1919', ['history','reality', 'bureaucracy'], ['accuse'], ['official'], long, chapter, ['Qing'], 0),
    book('Zeng Guofan', 'Tang Haoming', 'after 1976', ['history', 'biography'], ['story'], ['Zeng Guofan'], long, chapter, ['Qing'], 0),
    book('Bai Lu Yuan', 'Chen Zhongshi', 'after 1976', ['family', 'democracy', 'struggle', 'spirit'], ['realistic', 'rethink'], ['family'], long, chapter, ['Shanbei', 'countryside'], 0),
    book('Chang Hen Ge', 'Wang Anyi', 'after 1976', ['female', 'fate', 'change', 'love'], ['cold', 'humor', 'sad'], ['women'], long, chapter, ['Shanghai', '40s','90s'], tragedy),
    book('Yan Yang Tian', 'Han Ran', '1949-1976', ['classes', 'local', 'optimistic'], ['realistic', 'intricate'], ['farmer'], long, chapter, ['countryside', '1949'], 0),
    book('He Hua Dian', 'Sun Li', '1919-1949', ['husband', 'wife', 'nation', 'humanity'], ['poetry', 'detail'], ['farmer', 'woman'], short, chapter, ['Anti-Japanese War'], 0),
    book('Shou Jie', 'Wang Zengqi', 'after 1976', ['love', 'life', 'liberate'], ['poetry', 'refining' , 'flat'], ['youth', 'monk'], short, chapter, ['countryside'], 0),
    book('Fu Zao', 'Jia Pingwa', 'after 1976', ['love', 'society', 'fickleness'], ['simlple', 'intricate'], ['youth'], long, chapter, ['1978','countryside'], 0),
    book('Yong Zheng Huang Di', 'Er Yuehe', 'after 1976', ['history', 'biography'], ['story'], ['Yong Zheng'], long, chapter, ['Qing'], 0),
    book('Jing Hua Yan Yun', 'Lin Yutang', '1919-1949', ['love', 'society', 'history', 'feud', 'change'], ['English', 'intricate', 'profound','Hong Lou Meng'], ['youth'], long, chapter, ['history','war'], 0),
    book('Chu Liu Xiang', 'Gu Long', 'after 1976', ['swordsman'], ['story', 'intricate'], ['paladin'], long, chapter, ['Jiang Hu'], 0),
    book('Huo Zhe', 'Yu Hua', 'after 1976', ['time', 'life', 'family','death'], ['realistic', 'intricate', 'stroy'], ['life', 'old man'], long, chapter, ['history'], tragedy),
    book('Cheng Nan Jiu Shi', 'Lin Haiyin', '1949-1976', ['memory', 'life', 'childhood'], ['autobiography', 'stroy'], ['self'], long, chapter, ['20s', 'Peking'], 0),
    book('Fu Rong Zheng', 'Gu Hua', 'after 1976', ['society', 'change', 'local', 'tradition'], ['complex', 'stroy', 'beautiful'], ['man', 'woman'], long, chapter, ['South', 'countryside'], tragedy) ,
    book('Wei Yang Ge', 'Lu Qiao', '1919-1949', ['love', 'friendship', 'ideal', 'school'], ['optimistic', 'stroy', 'beautiful'], ['youth', 'student'], long, chapter, ['Anti-Japanese War', 'school'], 0)
    ].

%predict
function1 :-
    (
    writeln('attention please! if you don not know a question clearly, enter 0.\n') ,
    writeln('which period this novel was writen in?\n 1. before 1919  2.  1919 to 1949  3. 1949 to 1976  4. after 1976') , readInput(Period, [0, 1, 2, 3, 4]) ,
    writeln('\nlength of this novel?\n1. short   2. medium   3. long') , readInput(Length, [0, 1, 2, 3]) ,
    writeln('\ntype of this novel?\n1. chapter  2. collection  3. dictionary  4. note  5. diary') , readInput(Type, [0, 1, 2, 3, 4, 5]) ,
    writeln('\ntopic of this novel?') , read(Topic) ,
    writeln('\nwriting style of this novel?') , read(Style) ,
    writeln('\nending of this novel is tragedy?\n1. yes   2. no') , readInput(Ending, [0, 1, 2]) ,
    writeln('\nmain characters of this novel?') , read(Character) ,
    writeln('\nbackground of this novel?') , read(Background) ,
    bookData(Data) , member(Book, Data) , period_input(Period, Book) , bookLength_input(Length, Book) , type_input(Type, Book) , topic_input(Topic, Book) ,
    style_input(Style, Book) , ending_input(Ending, Book) , character_input(Character, Book) , background_input(Background, Book) ,
    title(Title, Book) , author(Author, Book) , writeln('\n\nnovel is:') , write(Title','Author)

    ) ; writeln('\nsorry, no book matches!').

%查询%
function2 :-
    writeln('enter title of the novel you want to inquire about:') , read(Title) ,
    bookData(Data) , member(Book, Data) , title(Title, Book) , bookInformation(Book) .

function3 :-
    writeln('attention please! enter keywords in a list format.') ,
    writeln('enter keywords:') , readList(Keywords) ,
    bookData(Data) , match(Keywords, Data, List) , length(List, Len) , write(Len) , printTitleList(List).

printTitleList(List) :-
    length(List, Length) ,
    (Length = 0 ;
    (not(Length = 0) , writeln('\n\nnovel:') ,  not( (member(Book, List) , title(Title, Book) , author(Author, Book) , writeln(Title','Author) , not(member(Book, List))) ))).

printTitleList(List, TitleInput) :-
    length(List, Length) ,
    (Length = 0 ;
    (not(Length = 0) , writeln('\n\nnovel:') ,  not( (member(Book, List) , title(Title, Book)  , not(Title = TitleInput) , author(Author, Book) , writeln(Title','Author) , not(member(Book, List))) ))).

%按照关键字匹配书籍%
match([], List, NewList) :- append(List, [], NewList) , !.

match([Keyword|Rest], List, NewList) :-
    matchOneWord(Keyword, List, TempList) , match(Rest, TempList, NewList) .

matchOneWord(Keyword, List, NewList) :-
    bagof(Book, (member(Book, List) , isAttribute(Keyword, Book)), NewList).

%匹配一次后禁止回溯%
isAttribute(Keyword, Book) :-
    (title(Keyword, Book) ; author(Keyword, Book) ; period(Keyword, Book) ; topicOf(Keyword, Book) ; styleOf(Keyword, Book) ; bookLength(Keyword, Book) ;
    backgroundOf(Keyword, Book) ; ending(Keyword, Book) ; type(Keyword, Book) ; characterOf(Keyword, Book) ) , !.


%第一次输入正确后，禁止回溯%
readInput(Input, List) :-
    (read(Input) , member(Input, List) , !) ;
    (writeln('invalid input, again please!') , readInput(Input, List)).

readList(Input) :-
    (read(Input) , is_list(Input) , !) ;
    (writeln('invalid input, again please!') , readList(Input)).

function4 :-
    writeln('enter title of a novel:') , read(Title) ,
    bookData(Data) , member(Book, Data) , title(Title, Book) ,
    ending(Ending, Book) , topic(Topic, Book) , background(Background, Book) , character(Character, Book) ,
    (
    (member(T, Topic) , member(B, Background) , member(C, Character) ,  (match([T, B, C, Ending], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , member(B, Background) , member(C, Character) ,  (match([T, B, C], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , member(B, Background) , (match([T, B, Ending], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , member(C, Character) ,  (match([T, C, Ending], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , member(B, Background) , (match([T, B], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , (match([T, Ending], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , member(C, Character) ,  (match([T, C], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !) ;
    (member(T, Topic) , (match([T], Data, List) , not(length(List, 1))) , printTitleList(List, Title) , !)
    ).

function5 :-
    writeln('enter title of a novel:') , read(Title) ,
    bookData(Data) , member(Book, Data) , title(Title, Book) ,
    type(Type, Book) , bookLength(Length, Book) ,  style(Style, Book) , period(Period, Book) ,
    (
    (member(S, Style) , (match([Type, Length, Period, S], Data, List) , not(length(List, 1))) , length(List, Len) , write(Len) , printTitleList(List, Title) , !) ;
    (member(S, Style) , (match([Type, Period, S], Data, List) , not(length(List, 1))) , length(List, Len) , write(Len) , printTitleList(List, Title) , !) ;
    (member(S, Style) , (match([Type, S], Data, List) , not(length(List, 1))) , length(List, Len) , write(Len) , printTitleList(List, Title) , !) ;
    (member(S, Style) , (match([Period, S], Data, List) , not(length(List, 1))) , length(List, Len) , write(Len) , printTitleList(List, Title) , !) ;
    (member(S, Style) , (match([S], Data, List) , not(length(List, 1))) , length(List, Len) , write(Len) , printTitleList(List, Title) , !) ;
    ((match([Type, Period], Data, List) , not(length(List, 1))) , length(List, Len) , write(Len) , printTitleList(List, Title) , !)
    ).


chooseFunction(X) :-
    (X = 1 , function1) ;
    (X = 2 , function2) ;
    (X = 3 , function3) ;
    (X = 4 , function4) ;
    (X = 5 , function5) .

start :-
    writeln('please choose function: \n 1.predict   2.inquire(according to title)   3.recommend(according to keywords)  4.recommend(according to book content)  5.recommend(according to book style)') ,
    readInput(Input, [1,2,3,4,5]) , chooseFunction(Input).
