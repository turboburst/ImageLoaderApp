This is a project that can fetch images from an endpoint url and show it in android device.

Problems:
1. the endpoint url is not available anymore, according to response, it should be replace by another url.

2. when I get response by OKHTTP, its response body is not the format I want, solution is translate it to inputstream and use XML tools to get the value of nodes, then get the class and object I want.

3. after I show the images, I found it is not smooth when I slide pictures. solution is change the Picasso's way to load image

Reason my choices:
1. I choose okhttp, because for all web request tools, this is very stable and efficient, and many other third part tools are based on it.

2. The structure I build this app, because I believe in this structure, it can be maintained easily, if there is a need to extend more features, it is easy to do.

More things I might do:
1. may change okhttp to retrofit, reason is retrofit is more efficient.

2. may reconstruct the app according to requirement and add more function and models.


Commit History:
1. init commit, build the app, organize basic structure, such as base activity and necessary models

2. decide use okhttp to request the url and get response.

3. translate response to objects I need, get arraylist of images.

4. use recyclerview to show images.

5. click image, will show image informations

6. show alert when response failed or arraylist returned is null
 