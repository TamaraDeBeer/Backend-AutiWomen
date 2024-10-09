-- Wijzig de kolom 'text' in de 'forums' tabel naar VARCHAR(4000)
ALTER TABLE forums ALTER COLUMN text TYPE VARCHAR(4000);

-- Wijzig de kolom 'text' in de 'comments' tabel naar VARCHAR(2000)
ALTER TABLE comments ALTER COLUMN text TYPE VARCHAR(2000);

-- Een unieke constraint om ervoor te zorgen dat er geen dubbele gebruikersnamen zijn
ALTER TABLE users
    ADD CONSTRAINT unique_username UNIQUE (username);

ALTER TABLE forums
    ALTER COLUMN comments_count SET DEFAULT 0;
ALTER TABLE forums
    ALTER COLUMN likes_count SET DEFAULT 0;
ALTER TABLE forums
    ALTER COLUMN views_count SET DEFAULT 0;

-- Insert a new user
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses,
                   autism_diagnoses_year, profile_picture_url)
VALUES ('Tamara', 'Tamara24!', 'Tamara.debeer@hotmail.com', 'random_api_key', true, 'Tamara', 'Female', '1991-07-06',
        'Ja', 2020, 'http://localhost:1991/images/Tamara.jpg'),
       ('Ariel', 'Ariel24!', 'Ariel@ariel.nl', 'random_api_key', true, 'Ariel', 'Female', '2004-08-23', 'Ja', 2010,
        'http://localhost:1991/images/Ariel.jpg'),
       ('Elsa', 'Elsa24!', 'Elsa@Elsa.nl', 'random_api_key', true, 'Elsa', 'Female', '2002-06-20', 'Ja', 2018,
        'http://localhost:1991/images/Elsa.jpg'),
       ('Jane', 'Jane24!', 'Jane@Jane.nl', 'random_api_key', true, 'Jane', 'Female', '2000-02-05', 'Ja', 2015,
        'http://localhost:1991/images/Jane.jpg'),
       ('Belle', 'Belle24!', 'Belle@belle.nl', 'random_api_key', true, 'Belle', 'Female', '1995-12-06', 'Ja', 2020,
        'http://localhost:1991/images/Belle.jpg'),
       ('Aurora', 'Aurora24!', 'Aurora@aurora.nl', 'random_api_key', true, 'Aurora', 'Female', '1945-12-06', 'Ja', 1963,
        'http://localhost:1991/images/Aurora.jpg'),
       ('Tiana', 'Tiana24!', 'Tiana@tiana.nl', 'random_api_key', true, 'Tiana', 'Female', '1955-09-15', 'Ja', 1980,
        'http://localhost:1991/images/Tiana.jpg'),
       ('Sarabi', 'Sarabi24!', 'Sarabi@sarabi.nl', 'random_api_key', true, 'Sarabi', 'Female', '1988-02-09', 'Ja', 2019,
        'http://localhost:1991/images/Sarabi.jpg'),
       ('Nala', 'Nala24!', 'Nala@nala.nl', 'random_api_key', true, 'Nala', 'Female', '1993-11-11', 'Ja', 2022,
        'http://localhost:1991/images/Nala.jpg'),
       ('Moana', 'Moana24!', 'Moana@moana.nl', 'random_api_key', true, 'Moana', 'Female', '1980-05-28', 'Ja', 2000,
        'http://localhost:1991/images/Moana.jpg');

-- Insert the corresponding authority for the user
INSERT INTO authorities (username, authority)
VALUES ('Tamara', 'ROLE_ADMIN'),
       ('Ariel', 'ROLE_USER'),
       ('Elsa', 'ROLE_USER'),
       ('Jane', 'ROLE_USER'),
       ('Belle', 'ROLE_USER'),
       ('Aurora', 'ROLE_USER'),
       ('Tiana', 'ROLE_USER'),
       ('Sarabi', 'ROLE_USER'),
       ('Nala', 'ROLE_USER'),
       ('Moana', 'ROLE_USER');


-- Insert a profile
INSERT INTO profiles (id, username, bio, name, date)
VALUES (5000, 'Tamara', 'Ik ben Tamara en ik hou van mijn huisdieren.', 'Tamara', '2024-01-01'),
       (5001, 'Ariel', 'Ik ben Ariel en ik hou van water', 'Ariel', '2024-02-01'),
       (5002, 'Elsa', 'Ik ben Elsa en ik hou van de koud', 'Elsa', '2024-03-01'),
       (5003, 'Jane', 'Ik ben Jane en ik hou van de jungle', 'Jane', '2024-04-01'),
       (5004, 'Belle', 'Ik ben Belle en ik hou van boeken', 'Belle', '2024-05-01'),
       (5005, 'Aurora', 'Ik ben Aurora en ik hou van slapen', 'Aurora', '2024-06-01'),
       (5006, 'Tiana', 'Ik ben Tiana en ik hou van koken', 'Tiana', '2024-07-01'),
       (5007, 'Sarabi', 'Ik ben Sarabi en ik hou van mijn zoon en mam', 'Sarabi', '2024-08-01'),
       (5008, 'Nala', 'Ik ben Nala en ik hou ben onafhankelijk', 'Nala', '2024-09-01'),
       (5009, 'Moana', 'Ik ben Moana en ik hou van de zee', 'Moana', '2024-10-01');

-- Insert a forum
INSERT INTO forums (id, name, age, title, text, date, topic, user_id)
VALUES (1000, 'Tamara', '1991-07-06', 'Verdriet',
        'Ik ben in het afgelopen jaar mijn vader en opa verloren. Mijn vader kreeg 8 jaar geleden darmkanker. Opeens kon hij niet meer poepen en hij eindigde in het ziekenhuis, toch wisten ze niet wat er was. Ik was niet in het land, maar op aftand vrees ik de K al. En ja de K was waar: darmkanker. Hij was bijna overleden in het Gooi Moord maar gelukkig had mijn moeder ervaring (ze had zelf borstkanker met uitzaaiingen toen ze 37 jaar jong was) en eiste ze verplaatsing naar het AVL ziekenhuis. Ik was weer in het land en ging met ambulance met mijn vader naar het AVL. Daar werd hij vrij direct meegenomen naar de operatietafel (hij zou anders de nacht niet overleefd hebben). En daarna stoma, chemo, HiPec Operatie, Chemo en alles leek wel. Maar na een jaar was hij opeens terminaal. Iets meer dan 5 jaar streed hij en onderging chemo maar uiteindelijk verloor hij de strijd. De laatste paar maanden waren echt erg en er volgde zelfs een opluchting toen hij op 1 januari 2024 stierf. Pff het werd een zware tijd en toen op 29 februari kreeg mijn (gezonde) opa een zware CVA en herstelde niet meer en stierf op 17 maart. Toevallig was ik nog bij mijn opa & oma op 28 februari en dat was een hele gezellige avond. Ik had gewild dat hij met de CVA was overlijden. Ik kom het niet aan om hem daarna te zien, ik was er bijna elke dag maar ik kon alleen maar huilen. Het was mijn opa, maar het was mijn opa niet… Ik schrijf dit niet om advies te vragen, maar deel graag wat jullie meemaken omtrent rouw.',
        '2024-07-06', 'Rouw', 'Tamara'),
       (1001, 'Elsa', '2002-06-20', 'Krijg jij je werkgever zover om aanpassingen voor je te maken?',
        'Ik werk al jaren voor een fijn bedrijf maar opeens zijn er veranderingen. Flex werkplekken, mijn hel. Mijn werkgever weet niet dat ik autistisch ben. Moet ik het hem vertellen om een vaste werkplek te kunnen hebben of hebben jullie andere tips?',
        '06-08-2024', 'Werk', 'Elsa'),
       (1002, 'Tamara', '1991-07-06', 'Hoe ga je om met het UWV?',
        'Ik zit al enkele jaren in de WIA. 2 jaar geleden werd ik medisch gekeurd en was de conclusie dat ik per feb-2025 24 uur per week moet kunnen werken om mijn huidige inkomsten te kunnen behouden. Omdat ik al langere jaren in een uitkering zit kreeg ik 2 jaar de tijd. Ik begon in mei 2023 met een opleiding van een jaar maar door persoonlijke omstandigheden liep in een half jaar vertraging op… Maar in feb 2025 krijg ik minder geld, kan ik het UWV hierover bellen en zullen ze begripvol zijn?',
        '06-07-2024', 'Werk', 'Tamara'),
       (1003, 'Ariel', '2004-08-23', 'Waar begin jij in het huishouden?',
        'Ik hou heel erg van een schoon huis maar het is altijd zo een nachtmerrie om er aan te beginnen. Dan zit erover na te denken, uren lang: waar begin ik? Moet ik eerst stoffen of eerst stofzuigen? Begin ik in de slaapkamer of in de keuken? En dus stel ik het soms dagen uit tot het echt niet meer kan. En dan nog lukt het amper. De waslijst groeit en dan zie ik al helemaal het bos tussen de bomen niet meer. Hebben andere autisten hier ook last van? En hoe pakken jullie dit aan? ',
        '06-06-2024', 'Structuur', 'Ariel'),
       (1004, 'Ariel', '2004-08-23', 'Hoe bereid je je voor op vakantie?',
        'Begrijp me niet verkeerd, wanneer ik eenmaal op vakantie ben vind ik het geweldig. Maar van te voren heb ik er nooit zin in, ik wil niet weg. Alles daar is nieuw. Ik heb er geen zin in. Mijn ouders betalen voor de vakantie en met moeite fake ik enthousiasme want ik wil zeker niet ondankbaar zijn. Ook omdat ik dus weet wanneer ik onderweg ben ik het leuk vindt en dat los kan laten. Is er iets wat ik doen om die tegenzin te verwijderen?',
        '06-09-2024', 'Mentaal', 'Ariel'),
       (1005, 'Sarabi', '1988-02-09', 'Maar je lijkt helemaal niet autistisch...',
        'Ik word er zo moe van dat wanneer ik deel dat ik autistisch ben mensen me niet eens lijken te geloven. Alsof ze zelf experts zijn in autisme. Dan moet ik gaan verantwoorden welke symptonen ik heb. En nog erger dan zeggen ze "maar dat heb ik ook" en moet ik gaan uitleggen waarom dat niet hetzelfde is. Bovendien vind ik het zelf soms ook lastig uit te leggen waarom zij dan geen autisme hebben. Ook familieleden die ik het vertel, zij denken mij te kennen omdat ik sociaal ben etc. maar zien niet hoe uitgeput ik daarna ben en lijken me dan niet te geloven of zeggen dat de hele familie autistisch is. Dat voelt heel denegrerend naar mijn struggles. Dus praat ik maar niet zoveel over, maar het is wel een deel van mij die ik graag deel met de mensen om mij heen. Hoe gaan jullie hiermee om?',
        '06-08-2024', 'Mentaal', 'Sarabi'),
       (1006, 'Aurora', '1945-12-06', 'Hoe ga je om met oppassen?',
        'Ik ben oma en mijn kinderen willen graag dat ik op mijn kleinkinderen pas. Niet alleen omdat ik hun daarbij help maar ook omdat ze willen dat ik daardoor een betere bant met mijn kleinkinderen krijg. Nu vind ik oppassen heel zwaar en moet ik er dagen van bij komen. Dat lijken ze niet te snappen. Begrijp me niet verkeerd, ik houd zielsveel van mijn kleinkinderen en vind het geweldig om ze te zien. Maar liever niet in mijn uppie met oppassen. Hoe leg je dit aan je kinderen uit? P.S. Het zijn trouwens nog erg jonge kinderen (0-5 jaar).',
        '06-07-2024', 'Familie', 'Aurora'),
       (1007, 'Ariel', '2004-08-23', 'Hoe kan ik structuur in mijn dag krijgen?',
        'Ik struggle heel erg. Ik wil graag structuur in mijn dag omdat ik zeker weet dat ik daar beter op gedij maar ik krijg het gewoon niet voor elkaar. Ik wil niet opstaan, kan me slechts een paar uur concentreren en ik wil niet naar bed. Ik voel mijn buik rommelen maar geen zin om te eten. Ik weet het gewoon even niet meer. Ik ben vast niet de enige, help?!',
        '06-06-2024', 'Familie', 'Ariel'),
       (1008, 'Belle', '1995-12-06', 'Ik kan ’s nachts niet slapen…',
        'Ik heb een aardig goed ritme en “slaap” van 11pm tot 8am maar eigenlijk slaap ik niet. Ik lig vaak tot 3 uur wakker en slaap dus te weinig. Ik zat aan de quetiapine, maar ik wil daar niet aan zitten. Ik kon daarmee mijn bed niet uitkomen, met weinig slaap gek genoeg wel. Maar ik val vaak ’s middags in slaap op de bank, ondanks koffie… en dan lukt het me ook weer niet om wakker te worden en moet ik echt een paar uur slapen. Bleh…',
        '06-09-2024', 'Fysiek', 'Belle'),
       (1009, 'Elsa', '2002-06-20', 'Kan ik Pabo studeren als autist?',
        'Ik ben er laatst achter gekomen dat ik ASS heb/ben (wat is de juiste terminologie?) en ik was van plan om PABO te doen maar zou ik dit aankunnen als autist? ',
        '06-08-2024', 'School', 'Elsa'),
       (1010, 'Moana', '1980-05-28', 'Waarom kan ik niet stoppen met TV kijken?',
        'Ik kijk veel TV. Ik HOUD van series kijken, al heb ik ze al eerder gezien. Ik heb Game of Thrones al wel 10 keer gekeken! En nog blijf ik kijken en ontdek ik nieuwe dingen en wil ik blijven kijken. Ik kijk uit naar mijn favoriete afleveringen! De meeste mensen om me heen vinden 1x kijken genoeg. Hoe staan jullie hierin?',
        '06-07-2024', 'test topic', 'Moana'),
       (1011, 'Ariel', '1958-10-25', 'Dit is een test forum', 'banaan', '06-06-2024', 'test topic', 'Ariel');

-- Insert a comment
INSERT INTO comments (id, forum_id, name, text, date, user_id)
VALUES (2000, 1003, 'Moana',
        'Ik herken heel erg wat je zegt. Tegenwoordig beloon ik mezelf door mijn favoriete podcast op te zetten en gewoon gaan (soms wel met lang uitstelgedrag hoor maar anders mag ik van mezelf de podcast niet luisteren).',
        '10-06-2024', 'Moana'),
       (2001, 1003, 'Nala', 'Oef, kan je hulp inschakelen?', '06-07-2024', 'Nala'),
       (2002, 1003, 'Tamara',
        'Zo herkenbaar! Ik doe vaak kleine klusjes alvast de dag ervoor (of ''s avonds laat oeps), bijvoorbeeld het bed verschonen, alvast stoffen en een natte doek. Zodat ik dan alleen hoef stof te zuigen.',
        '06-07-2024', 'Tamara'),
       (2003, 1003, 'Sarabi', 'Ik heb huishoudelijke hulp ingeschakeld want het lukt mij ook echt niet.', '06-07-2024',
        'Sarabi'),
       (2004, 1005, 'Elsa',
        'Ik ben gewoon gestopt met er met mensen over te praten, daarom ik deze forum pagina wel heel fijn om mezelf in te herkennen.',
        '07-08-2024', 'Elsa'),
       (2005, 1005, 'Moana',
        'Ik zeg tegen mijn familie dat ze zonder officiële diagnose geen autisme hebben hihi, maar goed dat nodigt ook niet uit tot een gesprek.',
        '08-08-2024', 'Moana'),
       (2006, 1005, 'Belle',
        'Ik selecteer heel erg met wie ik erover praat. Ik heb een enkele vriend die het wel respecteert en snapt en 2 familieleden. Met hun deel ik erover wanneer ik dat wil en dit helpt mij heel erg.',
        '09-08-2024', 'Belle'),
       (2007, 1005, 'Tiana',
        'Mijn broer geloofd niet dat ik autisme heb want hij kende iemand die ECHT autistisch was en die vrouw was zo anders dan ik. Ik zie het maar als zijn kortzichtigheid maar inderdaad daardoor verstop je wel een stukje van jezelf en wordt er ook geen rekening met je gehouden.',
        '15-08-2024', 'Tiana'),
       (2008, 1005, 'Nala',
        'Wat rot om te lezen zeg! Ik heb een soort werkstuk over mijzelf en mijn autisme geschreven met hulp van mijn autisme psycholoog en hun onderzoek uitkomsten. Daarin staat ''bewijs'' van mijn autisme, hoe autisme bij mij werkt en tips. Mijn familie staat er heel erg voor open en helpt mij.',
        '20-08-2024', 'Nala'),
       (2009, 1005, 'Ariel', 'In mijn omgeving krijg ik ook vaak terug "jaja iedereen heeft nu een rugzakje"...',
        '01-09-2024', 'Ariel'),
       (2010, 1006, 'Ariel', 'Dat lijkt mij ook echt een hel om te doen!', '06-08-2024', 'Ariel'),
       (2011, 1006, 'Tiana',
        'Ik heb hetzelfde, ik pas ook op mijn kleinkinderen en ik ben dan echt een paar dagen van de kaart. Ik heb het mijn kinderen uitgelegd en ze begrijpen het gelukkig.',
        '15-08-2024', 'Tiana'),
       (2012, 1006, 'Nala', 'Tja toch het eerlijke & moeilijke gesprek aangaan?', '20-08-2024', 'Nala'),
       (2013, 1007, 'Sarabi',
        'Als je een partner hebt zou die je allicht, in ieder geval, kunnen helpen om ’s ochtends uit bed te komen. Voor mij is dat het lastigst. Ik weet niet wat voor jouw de grootste uitdaging is?',
        '07-07-2024', 'Sarabi'),
       (2014, 1007, 'Tamara',
        'Oef ja daar struggle ik altijd mee, soms gaat het even goed en dan gaat allicht weer jaren mis. Ik hoop dat iemand met de gouden oplossing komt!',
        '08-08-2024', 'Tamara'),
       (2015, 1007, 'Jane',
        'Er is denk ik geen gouden oplossing, het is zo persoonlijk. Zelfs als autistische vrouwen zijn we allemaal anders en zullen we allemaal onze eigen weg moeten vinden. Sorry dat ik geen beter nieuws heb.',
        '09-09-2024', 'Jane'),
       (2016, 1009, 'Tiana',
        'Heb je wel eens mee gelopen op een basisschool? Ik zou je aanraden om een paar weekjes mee te lopen op verschillende basisscholen om te ontdekken hoe je met de prikkels omgaat.',
        '08-10-2024', 'Tiana');

-- Insert a like
INSERT INTO likes (forum_id, user_id)
VALUES (1000, 'Ariel'),
       (1001, 'Ariel'),
       (1004, 'Ariel'),
       (1006, 'Ariel'),
       (1008, 'Ariel'),
       (1010, 'Ariel'),
       (1001, 'Elsa'),
       (1003, 'Elsa'),
       (1005, 'Elsa'),
       (1007, 'Elsa'),
       (1009, 'Elsa'),
       (1011, 'Elsa'),
       (1000, 'Jane'),
       (1001, 'Jane'),
       (1002, 'Jane'),
       (1003, 'Jane'),
       (1009, 'Jane'),
       (1004, 'Belle'),
       (1005, 'Belle'),
       (1010, 'Belle'),
       (1000, 'Aurora'),
       (1002, 'Aurora'),
       (1006, 'Aurora'),
       (1010, 'Aurora'),
       (1001, 'Tiana'),
       (1009, 'Tiana'),
       (1000, 'Sarabi'),
       (1006, 'Sarabi'),
       (1007, 'Sarabi'),
       (1000, 'Nala'),
       (1001, 'Nala'),
       (1002, 'Nala'),
       (1003, 'Nala'),
       (1004, 'Nala'),
       (1005, 'Nala'),
       (1006, 'Nala'),
       (1007, 'Nala'),
       (1008, 'Nala'),
       (1009, 'Nala'),
       (1010, 'Nala'),
       (1011, 'Nala'),
       (1004, 'Moana'),
       (1007, 'Moana'),
       (1009, 'Moana');

-- Insert a view
INSERT INTO views (forum_id, user_id)
VALUES (1000, 'Ariel'),
       (1001, 'Ariel'),
       (1002, 'Ariel'),
       (1003, 'Ariel'),
       (1004, 'Ariel'),
       (1005, 'Ariel'),
       (1006, 'Ariel'),
       (1007, 'Ariel'),
       (1008, 'Ariel'),
       (1009, 'Ariel'),
       (1010, 'Ariel'),
       (1011, 'Ariel'),
       (1000, 'Tamara'),
       (1001, 'Tamara'),
       (1002, 'Tamara'),
       (1003, 'Tamara'),
       (1004, 'Tamara'),
       (1005, 'Tamara'),
       (1006, 'Tamara'),
       (1007, 'Tamara'),
       (1008, 'Tamara'),
       (1009, 'Tamara'),
       (1010, 'Tamara'),
       (1011, 'Tamara'),
       (1001, 'Elsa'),
       (1003, 'Elsa'),
       (1005, 'Elsa'),
       (1006, 'Elsa'),
       (1007, 'Elsa'),
       (1009, 'Elsa'),
       (1011, 'Elsa'),
       (1000, 'Jane'),
       (1001, 'Jane'),
       (1002, 'Jane'),
       (1003, 'Jane'),
       (1006, 'Jane'),
       (1009, 'Jane'),
       (1004, 'Belle'),
       (1005, 'Belle'),
       (1006, 'Belle'),
       (1010, 'Belle'),
       (1000, 'Aurora'),
       (1002, 'Aurora'),
       (1006, 'Aurora'),
       (1010, 'Aurora'),
       (1001, 'Tiana'),
       (1006, 'Tiana'),
       (1007, 'Tiana'),
       (1008, 'Tiana'),
       (1009, 'Tiana'),
       (1000, 'Sarabi'),
       (1006, 'Sarabi'),
       (1007, 'Sarabi'),
       (1010, 'Sarabi'),
       (1000, 'Nala'),
       (1001, 'Nala'),
       (1002, 'Nala'),
       (1003, 'Nala'),
       (1004, 'Nala'),
       (1005, 'Nala'),
       (1006, 'Nala'),
       (1007, 'Nala'),
       (1008, 'Nala'),
       (1009, 'Nala'),
       (1010, 'Nala'),
       (1011, 'Nala'),
       (1000, 'Moana'),
       (1002, 'Moana'),
       (1004, 'Moana'),
       (1007, 'Moana'),
       (1009, 'Moana'),
       (1004, 'Moana'),
       (1007, 'Moana'),
       (1009, 'Moana');