import os


def sh(script):
    os.system("bash -c '{}'".format(str(script)))


games = {
    "awale": "games.awale.AwaleGame",
    "dominos": "games.dominos.DominosGame",
    "nim": "games.nim.NimGame",
    "otherGame": "games.otherGame.otherGame"
}

print("Chose a game :")

for i, key in enumerate(games):
    print("{}. {}".format(i, key))

index = int(input("Please write a number : "))

game = list(games.keys())[index]

print("Lauching '{}' :".format(game))

sh("java -cp build/libs/iialib.jar {}".format(games[game]))
