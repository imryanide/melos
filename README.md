# Melos 💫
An emotional indicator to music

## How does it work ?
- The app identifies the song that is currently being played.
- Fetches a bunch of parameters (danceability, loudness, acousticness, tempo) related to the song.
- Based on the parameters, it calculates the emotion(s) that the listener's feeling.
- Using the emotion, it actuates specific IOT devices to change the ambience of the room.

## Usage

1. Clone this repository and move into the repository
```bash
git clone https://github.com/shreyass-ranganatha/melos.git; cd melos
```

2. Install the requirements
```bash
python -m pip install -r requirements.txt
```

3. In the same terminal path, do.
```bash
./bin/melos 
```

4. Open the link `localhost:8003` in a browser of your choice

5. In the input box provided, drop your favourite Spotify song's shareable link and hit the Submit button,
and get a quick shareable one/two word listing of your song's emotion...

6. The Android App, can sense the Spotify song playing in the background all on it's own 💫

## What's left
1. We expect to build this onto IoT platforms, to experience events like, the lights dimming out for 
mellow songs, drapes opening for upbeat bright songs, etc.
