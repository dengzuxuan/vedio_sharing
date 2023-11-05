import { createContext } from 'react'

interface StoreContext {
  // tabBar
  clickItemValue: string
  setClickItemValue: (value: string) => void
}

const context = createContext<StoreContext>({
  clickItemValue: '',
  setClickItemValue: () => { }
})

const StoreProvider = context.Provider

export { context, StoreProvider }
