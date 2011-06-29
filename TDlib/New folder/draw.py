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
    print update.index;
    try:
        a=np.loadtxt("C:/Users/Simon Armstrong/Documents/tower defence/TDlib/out"+str(update.index)+".txt",dtype=np.int32)
    except:
        return False;
    ax.clear()
    colours=["black","orange","red","blue","gray","pink","green"]
    for row in a:
        x,y,c,n=row[0:4]
        vecs=row[4:]
        for i in range(4):
            if vecs[i*2]!=-1:
                dx=vecs[i*2]-x;dy=vecs[i*2+1]-y
                ax.add_patch(p.FancyArrow(x+dx*0.25,y+dy*0.25,dx*0.5,dy*0.5,width=0.002,length_includes_head=True,head_width=0.1,head_length=0.1))
        string="X"
        if n<100000:
            string=str(n)
        ax.text(x,y,string,color=colours[c],va="center",ha="center")

    fig.canvas.draw_idle()
    update.index+=1
    
update.index=300

update(0);

import wx
#wx.EVT_IDLE(wx.GetApp(), update)
wx.EVT_KEY_DOWN(wx.GetApp(), update)
pl.show()