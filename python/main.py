import os,youtube_dl,sys,requests,time,json

# 
# Requirements : 
# youtube-dl requests
#
# Library :
# ffmeg (https://stackoverflow.com/questions/30770155/ffprobe-or-avprobe-not-found-please-install-one)
#

PREFIX = os.path.join('..', 'tmp')

class YtLogger (object):
    '''
        Custom logger class that only log the exception / error to the screen (as the std output is used in standard operation)
    '''
    def debug(self, msg):
        pass

    def warning(self, msg):
        pass

    def error(self, msg):
        print(msg)

def search(query):
    '''
        Retrieve the first video corresponding to a given query
    '''

    with youtube_dl.YoutubeDL({'noplaylist': 'True', 'format': 'bestaudio', 'logger': YtLogger()}) as ydl:
        return ydl.extract_info(f'ytsearch:{query}', download=False)['entries'][0]['id']

def extract_playlist(id):
    '''
        Extract information about a playlist (take some time as it load every video of the playlist)
    '''
    os.makedirs(PREFIX + '/', exist_ok=True)
    ydl = youtube_dl.YoutubeDL({'outtmpl': os.path.join(PREFIX, '%(id)s.%(ext)s'), 'logger': YtLogger()})

    result = ydl.extract_info(
        'https://www.youtube.com/playlist?list='+id,
        download=False
    )

    if not 'entries' in result:
        print('This is not a valid playlist')
        sys.exit(1)

    return result['entries']

def extract_video(url, file):
    '''
        Download a video (or audio) file by chunk given an url
    '''
    r = requests.get(url, stream=True)
    with open(file, 'wb') as f:
        r.raise_for_status()
        for chunk in r.iter_content(chunk_size=8192):
            f.write(chunk)

def __fetch_mp3(url, filename):
    '''
        Private method that fetch the mp3 file if not already created
    '''
    if not os.path.isfile(filename):
        extract_video(url, filename)

def fetch_mp3(id):
    '''
        Fetching the information about the mp3 youtube video and select the format matching m4a
    '''
    filename = os.path.join(PREFIX, 'v_' + id + '.mp3')
    if os.path.isfile(filename):
        return filename

    ydl = youtube_dl.YoutubeDL({
        'format': 'bestaudio/best',
        'outtmpl': filename,
        'logger': YtLogger(),
        'postprocessors': [{
            'key': 'FFmpegExtractAudio',
            'preferredcodec': 'mp3',
            'preferredquality': '192',
        }],
    })

    result = ydl.download([id])
    return filename


def __fetch_playlist(id):
    '''
        Private method that fetch information about a playlist (every hour) use of buffered information
    '''
    filename = os.path.join(PREFIX, '~' + id + '.json')
    now = time.time()

    playlist = {}

    if os.path.exists(filename) and os.path.isfile(filename):
        with open(filename, 'r') as f:
            playlist = json.loads(f.read())

        if time.time() - playlist['time'] < 3600:
            return playlist
    
    result = extract_playlist(id)

    playlist = {}
    playlist['time'] = time.time()
    playlist['ids'] = [i['id'] for i in result]
    with open(filename, 'w') as f:
        f.write(json.dumps(playlist))

    return playlist

def fetch_playlist(id):
    '''
        Fetch a playlist, download all the audio and finally return their filename
    '''
    playlist = __fetch_playlist(id)

    # fetch all the videos
    return [fetch_mp3(id) for id in playlist['ids']]

#
# Syntax Usage :
#  > python main.py playlist <playlist-id>
#  > python main.py audio <video-id>
#  > python main.py search <query-multiple-word-supported>
#

def main():
    if len(sys.argv) < 3:
        sys.exit(1)
    
    v = sys.argv[2]

    if sys.argv[1] == 'playlist':
        print('\n'.join(fetch_playlist(v)))
    
    elif sys.argv[1] == 'audio':
        print(fetch_mp3(v))
    
    elif sys.argv[1] == 'search':
        print(search(' '.join(sys.argv[2:])))

    else:
        sys.exit(1)

if __name__ == "__main__":
    main()
