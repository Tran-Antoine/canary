import os,youtube_dl,sys,json

class YtLogger (object):
    '''
        Custom logger class that only log the exception / error to the screen (as the std output is used in standard operation)
    '''
    def debug(self, msg):
        if '--verbose' in sys.argv:
            print(msg)

    def warning(self, msg):
        if '--verbose' in sys.argv:
            print(msg)

    def error(self, msg):
        print(msg)

def __extract_format(j):
    data = {}
    data['url'] = j['url']
    data['ext'] = j['ext']
    data['filesize'] = j['filesize']
    return data

def __extract_video_information(j):
    data = {}
    data['id'] = j['id']
    data['title'] = j['title']

    # find the formats
    # (try to find .mp3)
    
    mp3_filter = [i for i in j['formats'] if i['ext'] == 'mp3']
    m4a_filter = [i for i in j['formats'] if i['ext'] == 'm4a']
    
    if len(mp3_filter) > 0:
        data['format'] = __extract_format(mp3_filter[0])

    elif len(m4a_filter) > 0:
        data['format'] = __extract_format(m4a_filter[0])

    return data

def search(query):
    '''
        Retrieve the first video corresponding to a given query
    '''

    with youtube_dl.YoutubeDL({'noplaylist': 'True', 'format': 'bestaudio', 'logger': YtLogger()}) as ydl:
        return __extract_video_information(ydl.extract_info(f'ytsearch:{query}', download=False)['entries'][0])

def fetch_playlist(id):
    '''
        Fetch information about a playlist
    '''
    with youtube_dl.YoutubeDL({'logger': YtLogger()}) as ydl:
        result = ydl.extract_info(f'https://www.youtube.com/playlist?list={id}', download=False)
        return [__extract_video_information(i) for i in result['entries']]

def fetch_video(id):
    '''
        Fetch information about a single video id
    '''
    with youtube_dl.YoutubeDL({'logger': YtLogger()}) as ydl:
        return __extract_video_information(ydl.extract_info(f'https://www.youtube.com/watch?v={id}', download=False))

def main():
    if len(sys.argv) < 3:
        sys.exit(1)

    v = sys.argv[2]

    if sys.argv[1] == 'playlist':
        print(fetch_playlist(v))
    
    elif sys.argv[1] == 'video':
        print(fetch_video(v))
    
    elif sys.argv[1] == 'search':
        print(search(' '.join(sys.argv[2:])))
    
    else:
        sys.exit(1)

if __name__ == '__main__':
    main()

