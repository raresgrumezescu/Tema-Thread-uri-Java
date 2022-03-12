In rezolvarea acestei teme am procedat in felul urmator: am creat o serie de clase in care sa rezolv diferite
probleme cu care m-am confruntat de-a lungul rezolvarii acestei teme. Clasele se numesc in felul urmator:

- Tema2 (clasa main)
- MyThread (clasa folosita de thread-urile din etapa Map si la crearea rezultatelor obtinute la finalul acestei etape)
- MyThread2 (clasa folosita de thread-urile din etapa Reduce si la crearea de output-uri ce urmeaza sa fie scrise in 
		fisierele out)
- Task (clasa pe care o folosesc sa creez obiecte folosite ca task-uri in etapa Map)
- TaskAgain (clasa pe care o folosesc sa creez obiecte folosite ca task-uri in etapa Reduce)
- Task2 (clasa pe care o folosesc sa creez obiecte folosite ca task-uri in etapa Reduce)
- Rezultat (clasa pe care o folosesc sa salvez sub forma de obiecte rezultatele obtinute in etapa Map)
- Outputuri (clasa pe care o folosesc sa salvez sub forma de obiecte rezultatele obtinute in etapa Reduce, adica
		liniile ce urmeaza sa fie scrise in fisierele output)
- SortByRank (clasa pe care o folosesc sa implementez un comparator folosit la sortarea rezultatelor de tip Outputuri)



Programul functioneaza in felul urmator:

Intai se citesc din fisierul de intrare informatiile: numele fisierelor ce trebuie parcurse, numarul de workeri si dimensiunea
unui fragment. Aceasta operatiune o realizez folosind BufferedReader.

Numele fisierelor le folosesc ca apoi sa le trimit ca parametru pe rand ca sa fie deschise, la randul lor, fisierele la
care fac referire. Deschid fiecare din aceste fisiere, apoi parcurg continutul lor si stabilesc pozitiile offset-urilor
pentru fiecare din task-urile ce urmeaza sa fie create. Creez obiectele de tip Task si le salvez intr-un vector taskuri.
Aceste obiecte contin un camp pentru numele fisierului, unul pentru offset si unul pentru dimensiunea fragmentului. De asemenea,
am mai adaugat campuri aditionale (id_task, id_fisier), ca sa le folosesc la diversele operatii de prelucrare de pe parcurs.

Dupa ce se creeaza vectorul de task-uri taskuri, creez thread-urile ce vor rezolva operatiile din etapa de Map; acestea primesc
ca parametru un id, folosit pentru parcurgerea vectorului de task-uri.

Modul in care functioneaza logica programului este urmatoarea:

Vectorul de task-uri va fi parcurs de fiecare thread in parte, insa numai anumite pozitii din el vor fi accesate de fiecare.
De exemplu, pentru 3 thread-uri si un vector de 9 task-uri, thread-ul 1 va parcurge numai pozitiile 1, 4 si 7 din vector,
thread-ul 2 numai 2, 5 si 8 iar ultimul thread pozitiile 3, 6 si 9.

Fiecare thread are un id unic. Cand parcurg in thread vectorul, folosesc un for de forma:
for (int i = id; i < vector.size(); i += nr_workeri), deci fiecare numai 3 pozitii va accesa din vectorul mare de task-uri.

Astfel un task va fi accesta numai de un thread si nu de mai multe.

In thread-urile de tipul MyThread am o metoda run acre face urmatoarele lucruri: actualizeaza offset-ul si dimensinuea fiecarui task,
parcurgand cu un contor fragmentul de fisier alocat fiecaruia, apoi parcurge fiecare fragment si prelucreaza informatia din el,
obtinand cate un dictionar de tip HashMap si un vector de cuvinte de lungime maxima. Acestea vor fi folosite pentru crearea
obiectelor de tip Rezultat, pe care le salvez intr-un vector de rezultate.

Dupa ce termin de folosit thread-urile, fac join pe fiecare.

In continuare, vectorul de rezultate il prelucrez, pe baza obiectelor din el creand noile obiecte de tip TaskAgain, si le pun
intr-un vector pe toate. Acest vector il voi folosi la etapa Reduce.
Acest vector il voi parcurge identic ca pe cel de Task-uri de la faza de Map, asa cum am explicat anterior.

In continuare, creez cate un obiect de tip TaskAgain care este practic un obiect ce contine: un nume de fisier,
o lista de dictionare (alcatuita din toate dictionarele alcatuite din cuvintele acelui fisier) si o lista
de liste de cuvinte maximale. Practic se concateneaza toate obiectele de tip Rezultat specifice unui fisier,intr-o structura
ce sa le inglobeze. Propriu-zis, aceasta structura va fi prelucrata in partea de Reduce in felul urmator:

In thread-urile corespunzatoare lui Reduce se va realiza etapa de combinare, adica formarea unui obiect de tip Task2
care sa contina urmatoarele lucruri: numele fisierului care se prelucreaza, un dictionar format din suprapunerea
dictionarelor din lista primita, si un vector de cuvinte de lungime maxima, adica practic elementul din 
vectorul de liste, care contine cuvintele cele mai lungi. Fieare obiect de tip Task2 astfel obtinut se prelucreaza in 
etapa de procesare.

In interiorul acestei etape (de procesare) calculez rangul pentru fiecare fisier in parte (Un obiect Task2 corespunde unui fisier).

Rezultatele de pe urma acestei prelucrari le stochez in alt vector, numit arr_out, de obiecte de tip Outputuri. Aceste obiecte
contin 2 campuri: unul output, ce contine efectiv continutul liniei ce trebuie scrise in fisierul de output, si un camp
rank, ce tine minte rangul fisierului corespunzator obiectului, ca cu ajutorul acestui camp sa sortez liniile in ordine
descrescatoare dupa rang.

In Tema2 fac join pe fiecare thread si vectorul il sortez folosid un comparator, creat cu ajutorul clasei SortByRank.
Acest comparator sorteaza vectorul de output-uri in functie de rangul fiecarui obiect de tip Outputuri.

La final, parcurg acest vector si scriu in ordine fiecare linie in fisierul de output.