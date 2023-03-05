from dotenv import load_dotenv
import os
import requests
import base64


class Spotify:
    """
    A Class to use Spotify API.

    :param song_id : The Song ID from spotify
    """

    def __init__(self, song_id):
        self.song_id = song_id


    def _get_auth_token(self):
        """
        To get the authentication token from spotify API.

        :return : The Authentication token
        
        """

        # Loading environment variables
        load_dotenv()
        client_id = os.getenv("CLIENT_ID")
        client_secret = os.getenv("CLIENT_SECRET")

        # Spotify API URL for token generation
        url = 'https://accounts.spotify.com/api/token'

        # Encoding clientID and clientSecret to base64
        base64_bytes = base64.b64encode((f"{client_id}:{client_secret}").encode("ascii"))
        clients = base64_bytes.decode("ascii")

        # Sending a POST request
        payload = {'grant_type': 'client_credentials'}
        headers = {
            "Content-Type" : "application/x-www-form-urlencoded",
            "Authorization": "Basic " + clients
        }

        response = requests.post(url, headers=headers, json=True, data=payload)
        if (response.status_code == 200):
            # Auth token is extracted from the returned JSON
            auth_token = response.json()['access_token']
            return auth_token
        return None


    def get_song_info(self):
        """
        To get the data related to the given Song.

        :return : a dictionary of required data (i.e., danceability, acoustiness, etc.)

        """

        if (auth_token := self._get_auth_token()) == None:
            return {}

        # Spotify API URL for extracting song data
        url = f"https://api.spotify.com/v1/audio-features/{self.song_id}"

        # Sending a GET request to retrieve data
        headers= {
            "Content-Type": "application/json",
            "Authorization": f"Bearer {auth_token}"
        }
        r = requests.get(url, headers=headers)

        if r.status_code == 200:
            retrieved_data = r.json()
            keys = ["acousticness", "danceability", "duration_ms", "energy", "instrumentalness", "liveness", "loudness", "mode", "speechiness", "tempo", "valence"]
            song_details = dict()
            for key in keys:
                song_details[key] = retrieved_data[key]
            return  song_details
        return {}
