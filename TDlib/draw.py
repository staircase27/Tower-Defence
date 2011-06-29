import numpy as np
import matplotlib
matplotlib.use("WXAgg")
import pylab as pl
import matplotlib.patches as p


fig=pl.figure()
ax=fig.add_subplot(111)
pl.ion()
ax.set_xlim(-1,11);
ax.set_ylim(-1,11);


def update(something):
    try:
        a=np.loadtxt("C:/Users/Simon Armstrong/Documents/tower defence/TDlib/outA"+str(update.index)+".txt",dtype=np.int32)
    except:
        return False;
    colours=["black","grey","blue","gray","red","orange","green"]
    if update.lables==None:
        update.lables={};
        for row in a:
            x,y,c,n,r=row[0:5]
            string="X"
            if n<100000:
                string=str(n)
            rstring="X"
            if r<100000:
                rstring=str(r)
            string=string+" "+rstring;
            update.lables[(x,y)]=ax.text(x,y,string,color=colours[c],va="center",ha="center")

        fig.canvas.draw_idle()

        print update.index;
        update.index+=1
    else:
        for row in a:
            x,y,c,n,r=row[0:5]
            string="X"
            if n<100000:
                string=str(n)
            rstring="X"
            if r<100000:
                rstring=str(r)
            string=string+" "+rstring;
            if update.lables[(x,y)].get_text()!=string:
                update.lables[(x,y)].set_text(string);
            if update.lables[(x,y)].get_color()!=colours[c]:
                update.lables[(x,y)].set_color(colours[c]);

        fig.canvas.draw_idle()

        print update.index;
        update.index+=1
    
update.index=0
update.lables=None;


update(0);

import wx
wx.EVT_IDLE(wx.GetApp(), update)
#wx.EVT_KEY_DOWN(wx.GetApp(), update)
pl.show()