import os,youtube_dl,sys,requests,time,json

class YtLogger (object):
    def debug(self, msg):
        pass

    def warning(self, msg):
        pass

    def error(self, msg):
        print(msg)

def extract_playlist(id):
    os.makedirs('./tmp/', exist_ok=True)
    ydl = youtube_dl.YoutubeDL({'outtmpl': './tmp/%(id)s.%(ext)s', 'logger': YtLogger()})

    result = ydl.extract_info(
        'https://www.youtube.com/playlist?list='+id,
        download=False
    )

    if not 'entries' in result:
        print('This is not a valid playlist')
        sys.exit(1)

    return result['entries']

def extract_video(url, file):
    r = requests.get(url, stream=True)
    with open(file, 'wb') as f:
        r.raise_for_status()
        for chunk in r.iter_content(chunk_size=8192):
            f.write(chunk)

def __fetch_mp3(url, filename):
    if not os.path.isfile(filename):
        extract_video(url, filename)

def fetch_mp3(id):
    filename = os.path.join('.', 'tmp', id)
    if os.path.isfile(filename + '.m4a'):
        return filename + '.m4a'

    url = 'https://www.youtube.com/watch?v' + id
    ydl = youtube_dl.YoutubeDL({'outtmpl': './tmp/%(id)s.%(ext)s', 'logger': YtLogger()})
    result = ydl.extract_info(
        'https://www.youtube.com/watch?v='+id,
        download=False
    )


    formats = [i['url'] for i in result['formats'] if i['ext'] == 'm4a']    
    if len(formats) == 0:
        return None

    __fetch_mp3(formats[0], filename + '.m4a')

    # convert_to_mp3(filename + '.m4a', filename + '.mp3')

    # os.remove(filename + '.m4a')
    return filename + '.m4a'


def __fetch_playlist(id):
    filename = os.path.join('.', 'tmp', '~' + id + '.json')
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
    playlist = __fetch_playlist(id)

    # fetch all the videos
    return [fetch_mp3(id) for id in playlist['ids']]

# print(extract_playlist('PLlQx9Mr0xn_OlW8qXsjD77D1sbP5x_U2H&jct=WlNZwskNlE0gIR1RYWbFfJtpbaUldg')[0])
# fetch_playlist('PLlQx9Mr0xn_OlW8qXsjD77D1sbP5x_U2H&jct=WlNZwskNlE0gIR1RYWbFfJtpbaUldg')
# fetch_mp3('CGwH6rZk7VM')
# extract_video('https://r5---sn-nfpnnjvh-9an6.googlevideo.com/videoplayback?expire=1631662694&ei=Bt5AYfudEpi8x_APhNaH6Ag&ip=178.194.185.71&id=o-ALE1MFbJHk1CCJoaQECrIKIraePjAa2gypTiVxTHReB7&itag=247&aitags=133%2C134%2C135%2C136%2C137%2C160%2C242%2C243%2C244%2C247%2C248%2C278&source=youtube&requiressl=yes&mh=mP&mm=31%2C29&mn=sn-nfpnnjvh-9an6%2Csn-1gieen7e&ms=au%2Crdu&mv=m&mvi=5&pl=22&initcwndbps=2190000&vprv=1&mime=video%2Fwebm&ns=vVpL9KnrlR3JnirtTVgH8KIG&gir=yes&clen=8031181&dur=176.000&lmt=1560454740991157&mt=1631640543&fvip=5&keepalive=yes&fexp=24001373%2C24007246&c=WEB&txp=5431432&n=PmkPAEBBQODAqp1A&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAM_hB8ts0CsucaHyAL27xJ7H3OPIaY6aK2gbOtdSr0eNAiAtett9KbGmV6JtwiC915G1BFeS7q16t0E5uf5r89Trlw%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRgIhAI8jVyYvfyi8sQUk2Elb7zjIEFnlkgB9Z4HEplcQ-PDrAiEA6ZTMlqc2CeX30cgVknMOYA6NQqmNw9mIjjVv5kRptQA%3D', './tmp/test.webm')

def main():
    if len(sys.argv) != 3:
        sys.exit(1)
    
    v = sys.argv[2]

    if sys.argv[1] == 'playlist':
        print('\n'.join(fetch_playlist(v)))
    
    elif sys.argv[1] == 'video':
        print(fetch_mp3(v))
    
    else:
        sys.exit(1)

if __name__ == "__main__":
    main()
