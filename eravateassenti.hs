
mylength [] = 0
mylength (_ : xs) = 1 + mylength xs

-- append (++) complessità lineare della lunghezza del parametro sinistro
append :: [a] -> [a] -> [a]
append [] ys = ys
append (x : xs) ys = x : append xs ys
-- alcuni esercizi (reimplementazione di funzioni già presenti)
-- error :: [char] -> a, è un tipo sospetto perchè ritorna a e non è presente nel dominio
myhead [] = error "myhead applicata alla lista vuota" 
myhead (x : _) = x

myreverse a =  aux a []
   where
      aux [] a = a
      aux (x:xs) a = aux xs (x:a)

-- è lineare
myconcat [] = []
myconcat (x:xs) = x ++ myconcat xs

myelem _ [] = False
myelem n (x:xs) = n == x || elem n xs
--elem x (x:_) = True non si può fare, dal punto di vista di chi scrive il compilatore è comodo impedire che la stesa variabile si possa scrivere 2 volte nel pattern, cosa vorrebbe dire altrimenti? è lo stesso puntatore? sono lo stesso elemento? sono due elementi uguali? ci possono essere diverse nozioni di equivalenza (fisica, definita a priori)

myget _ [] = error "index out of bounds"
myget n (x:xs) | n == 0 = x
               | otherwise = myget (n-1) xs

mytake 0 _ = []
mytake _ [] = []
mytake n (x:xs) = x: mytake (n-1) xs

mydrop 0 xs = xs
mydrop _ [] = []
mydrop n (_:xs) = mydrop (n-1) xs

mynull [] = True
mynull _ = False

myord [] = True
--myord [_] = True  la lista contiene un solo elemento
myord (x:[]) = True
myord (x:y:xs) = x <= y && myord (y:xs)

-- sommare tutti gli elementi di una lista di interi
mysum [] = 0
mysum (x:xs) = x + mysum(xs)

-- trovare l'elemento più grande di una lista di interi
mymax [x] = x
mymax (x:xs) = x `max` mymax xs

-- eliminare gli elementi duplicati da una lista ordinata
nodup_ord [] = []
nodup_ord [x] = [x]
nodup_ord (x:y:xs) | x == y = nodup_ord(y:xs)
                   | otherwise = x:nodup_ord(y:xs)
                     
-- eliminare gli elementi duplicati (suggerimento: usare elem)
nodup [] = []
nodup [x] = [x]

{-
nodup n = aux [] n
          where
            aux l [] = l
            aux l (x:xs) | elem x xs = aux l xs
                         | otherwise = aux (x:l) xs
-}
{-
 scrivere funzioni per
 1. calcolare il valore assoluto di tutti gli elementi di una lista
 2. elevare al quadrato tutti gli elementi di una lista
 3. creare una lista di elementi della forma (x,x) a partire da una lista di elementi x
 4. scambiare le componenti delle coppie in una lista di coppie
-}

absl [] = []
absl (x:xs) = abs x : absl xs
-- absl xs = map abs xs

quadl [] = []
quadl (x:xs) = pow x 2 : quadl xs

pairl [] = []
pairl (x:xs) = (x,x) : pairl(xs)

swapl [] = []
swapl ((x,y):xs) = (y,x) : swapl(xs)

-- sono funzioni diverse ma hanno la stessa struttura, posso scrivere una funzione che applica una determinata funzione a ciascuno elemento della lista
mymap _ [] = []
mymap f (x:xs) = f x : mymap f xs

{-
 1. funzione che elimina da una lista di interi tutti gli elementi minori o uguali a 3
 2. funzione che elimina da una lista di interi tutti lgi elementi pari
 3. funcione che elimina da una lista di liste tutte le liste vuote
-}

min_three [] = []
min_three (x:xs) | x <= 3 = min_three xs
                 | otherwise = x: min_three xs
                 
nopair [] = []
nopair (x:xs) | x `mod` 2 == 0 = nopair xs
              | otherwise = x:nopair xs
              
noempty [] = []
noempty (x:xs) | x == [] = noempty xs
               | otherwise = x:noempty xs
-- anche queste funzioni hanno la stessa struttura, cambia il tipo di test che faccio sugli elementi per decidere se l'elemento rimane o viene eliminato
myfilter _ [] = []
myfilter p (x:xs) | p x = x: myfilter p xs
                  | otherwise = myfilter p xs
                  
{-
 dedurre la funzione dal tipo
 (a -> Bool) -> [a] -> [a]  filter
 (a -> Bool) -> [a] -> Bool è vero che tutti gli elementi soddisfano il predicato?
 (a -> Bool) -> [a] -> ([a],[a]) separa, mette da una parte gli elenti che soddisfano il predicato, e dall'altra quelli che non lo soddisfano
-}
