/**
* @flow
*/

import { NativeModules, ToastAndroid } from 'react-native'
import bgTimer from 'react-native-background-timer'

const { BackgroundTaskBridge } = NativeModules

const charms = [
  {
    id: 'uuid1',
    name: 'First',
    cover: 'goodmorning',
  },
  {
    id: 'uuid2',
    name: 'Second',
    cover: 'night',
  }
]

type TaskInfo = {
  id: string,
}
export default async function widgetTask (taskData: TaskInfo) {
  const {id} = taskData || {}
  bgTimer.setTimeout(() => {
    synchronizeWidget()
    triggerCharm(id)
  }, 0)
}

export function synchronizeWidget () {
  ToastAndroid.show(`Initializing ...`, ToastAndroid.SHORT);
  BackgroundTaskBridge.initializeWidgetBridge(charms)
}

function triggerCharm (id) {
  if (!id) return
  ToastAndroid.show(`Triggering ${id}...`, ToastAndroid.SHORT);
}