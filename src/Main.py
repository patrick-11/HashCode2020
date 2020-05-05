class Book:
    def __init__(self, id, score):
        self.id = id
        self.score = score


class Library:
    def __init__(self, id, numBooks, signUpDays, shipBooksPerDay, books, totalScore):
        self.id = id
        self.numBooks = numBooks
        self.signUpDays = signUpDays
        self.shipBooksPerDay = shipBooksPerDay
        self.books = books
        self.totalScore = totalScore
        self.numSubmittedBooks = 0


def readFile(fileName):
    file = open("input/" + fileName, "r")

    def readNextLine():
        return list(map(int, file.readline().split()))

    totalBooks, totalLibraries, totalScanningDays = readNextLine()
    scoreOfBooks, libraries = [], []

    tokens = readNextLine()
    for i in range(len(tokens)):
        scoreOfBooks.append(Book(i, tokens[i]))

    count = 0
    while True:
        tokens = readNextLine()
        if not tokens:
            break

        numBooks, signUpDays, shipBooksPerDay = tokens
        tokens = readNextLine()
        books = []
        for i in range(len(tokens)):
            books.append(Book(tokens[i], scoreOfBooks[tokens[i]].score))

        totalScore = 0
        for book in books:
            totalScore += book.score

        libraries.append(Library(count, numBooks, signUpDays, shipBooksPerDay, books, totalScore))
        count += 1

    file.close()
    return totalBooks, totalLibraries, totalScanningDays, scoreOfBooks, libraries


def writeFile(fileName, signedUpLibraries, submittedBooks):
    space = " "
    newline = "\n"

    file = open("output/" + fileName, "w")
    file.write(str(len(signedUpLibraries)) + newline)

    for lib in signedUpLibraries:
        file.write(str(lib.id) + space + str(lib.numSubmittedBooks) + newline)
        for bookId in submittedBooks:
            if submittedBooks[bookId] == lib.id:
                file.write(str(bookId) + space)
        file.write(newline)
    file.close()


def main():
    # Choose file: a, b, c, d, e, f
    fileName = "a.txt"

    # Read Input file
    totalBooks, totalLibraries, totalScanningDays, scoreOfBooks, libraries = readFile(fileName)

    signedUpLibraries, submittedBooks = [], {}

    # Sort libraries
    # libraries.sort(key=lambda lib: -lib.shipBooksPerDay)
    # libraries.sort(key=lambda lib: -lib.totalScore)
    # libraries.sort(key=lambda lib: lib.signUpDays)

    # Best for file: a, b, c, d, f
    # libraries.sort(key=lambda lib: -lib.totalScore / lib.signUpDays)

    # Best for file: a, b, d, e,
    # libraries.sort(key=lambda lib: -(lib.totalScore*lib.shipBooksPerDay)/lib.signUpDays)

    # Start process of scanning
    dayOfNextSignUp = 0
    library = None
    for i in range(totalScanningDays):
        print("Day: ", i)
        if i == dayOfNextSignUp:
            # Add library to signedUpLibraries List after signUp
            if library:
                library.books.sort(key=lambda book: -book.score)
                signedUpLibraries.append(library)
            while libraries:
                library = libraries[0]
                libraries.pop(0)

                # Check if library fits in the remaining scanning days
                if library.signUpDays < (totalScanningDays - dayOfNextSignUp):
                    # Add days for the next library signUp
                    dayOfNextSignUp += library.signUpDays
                    break

        for j in range(len(signedUpLibraries)):
            lib = signedUpLibraries[j]
            for d in range(lib.shipBooksPerDay):
                if not lib.books:
                    break
                book = lib.books[0]
                if book.id not in submittedBooks:
                    submittedBooks.update({book.id: lib.id})
                    lib.numSubmittedBooks += 1
                else:
                    # //Decrement by one "book" because we did not find a book to submit
                    d -= 1
                lib.books.pop(0)

    # Remove libraries with no submitted books
    for library in list(signedUpLibraries):
        if library.numSubmittedBooks == 0:
            signedUpLibraries.remove(library)

    # Write Output file
    writeFile(fileName, signedUpLibraries, submittedBooks)

    # Prints score of the algorithm
    score = 0
    for key in submittedBooks:
        score += scoreOfBooks[key].score
    print("Total Score: ", score)


main()
